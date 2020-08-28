package com.example.demo.generate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.demo.generate.Contents.*;


/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/08/18 9:59
 * desc :
 */
public class GenerateUtils {
    public String classPath;
    public String packagePath;
    public String url;

    GenerateUtils(String url, String classPath) {
        if (StringUtils.isEmpty(url)) {
            throw new NullPointerException("url 不能为空");
        }
        if (StringUtils.isEmpty(classPath)) {
            this.classPath = System.getProperty("user.dir") + "\\src\\main\\java\\generate\\";
            packagePath = "generate";
        } else {
            this.classPath = System.getProperty("user.dir") + "\\src\\main\\java\\" + classPath.replaceAll("\\.", "\\\\") + "\\";
            packagePath = classPath;
        }
        this.url = url;

    }

    public void generate() throws Exception {
        //获取swagger 信息
        //swagger 中有"$" 字符需要删除掉，原因是无法解析 会被识别为特殊内容
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(new Request.Builder().get().url(url).build());
        Response execute = call.execute();
        String respones = execute.body().string();
        respones = respones.replaceAll("\\$", "");
        if (execute.code() != 200) {
            throw new Exception("url 地址错误" + respones);
        }
        //用户自定义url解析成爬出路径的url
        //
        String urlPath = "http://java2.demo.ehi.com.cn/Reporting-System/v2/api-docs";
        String substring = urlPath.substring(0, urlPath.lastIndexOf("/"));
        final String finalUrlPath = substring.substring(0, substring.lastIndexOf("/"));
        //转化成json 进行解析
        JSONObject jsonObject = JSON.parseObject(respones);
        //请求的http 如果之后适用于fegin 需要进行使用这个http www.reprot.cn
        String httpUrl = (String) jsonObject.get("host");
        //  /java
        String basePath = (String) jsonObject.get("basePath");
        // http   https
        String httpOrHttps = (String) jsonObject.get("schemes");
        String className = (String) getObject(jsonObject, "info.title");
        //实体类的 包括返回和入参 TODO 这里需要进入另一个方法去执行和主流没有直接联系
        JSONObject definitions = jsonObject.getJSONObject("definitions");
        //根据key value 将实体信息进行组合
        Set<Map.Entry<String, Object>> entries = definitions.entrySet();
        /*实体信息的组合 只解析单一的实体 如果名称中有"《"则不用解析*/
        //实体包路径和实体名称
        //pageinfo 为特殊类，没有单独存在的实体 这种特殊类建议 用户指定import 路径
        List<Model> models = new ArrayList<>();
        Map<String, String> modelpath = new HashMap();
        modelpath.put("Result", "import cn.seed.common.core.Result;\n");
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            //这种返回参数的key 直接忽略
            if (key.contains("«") || key.equals("Result")) {
                continue;
            }
            Model model = new Model();
            models.add(model);
            String rootPath = packagePath + DOT + MODELPATH + DOT + key + SPERATOR;
            //将实体的路径和名称 存放到
            modelpath.put(key, IMPORT + rootPath + BR);
            model.setModelName(key);
            //实体的包路径
            model.setPackagePath(PACKAGE + packagePath + DOT + MODELPATH + SPERATOR + BR);
            //需要导入包的内容
            Set<String> importProperties = new HashSet<>();
            List<MemberModel> memberModel = new ArrayList<>();
            model.setImportProperties(importProperties);
            model.setMemberModel(memberModel);
            JSONObject properties = ((JSONObject) entry.getValue()).getJSONObject("properties");
            Set<Map.Entry<String, Object>> members = properties.entrySet();
            for (Map.Entry<String, Object> member : members) {
                MemberModel memberMo = new MemberModel();
                String memberName = member.getKey();
                memberMo.setMemberName(memberName);
                JSONObject value = (JSONObject) member.getValue();
                //type 类型
                String type1 = value.getString("type");
                String type;
                if (type1 == null) {
                    type = value.getString("ref");
                } else {
                    type = ModelClassType.getClassName(value.getString("type"));
                }
                //需要导入list 包
                ContentsImport.getValue(type).ifPresent(item -> importProperties.add(item.name()));
                if (type.contains("#/definitions/")) {
                    type = type.replaceAll("#/definitions/", "");
                }
                memberMo.setMemberType(type);
                if (ModelClassType.isArray(type1)) {
                    Object object = getObject(value, "items.ref");
                    String itemName;
                    if (object == null) {
                        itemName = ((String) getObject(value, "items.type"));
                    } else {
                        itemName = ((String) object).substring(REFLENGTH);
                    }

                    memberMo.setItemName(itemName);
                    //需要导入对应实体的包
                    if (object != null && !itemName.equals(key)) {
                        importProperties.add(itemName);
                    }
                }
                //description 描述
                String description = value.getString("description");
                memberMo.setDescription(description == null ? "" : description);
                memberModel.add(memberMo);
            }
        }
        JSONArray tags = jsonObject.getJSONArray("tags");
        //所有的controller 名称和描述所有的接口名称
        List<Tags> tagsList = tags.toJavaList(Tags.class);
        final Map<String/*controller name*/, InterfaceMethodModel> interfaceMap =
                tagsList.stream()
                        .collect(Collectors.toMap((item) -> item.getName(),
                                item -> new InterfaceMethodModel(item.getName(),
                                        item.getDescription(),
                                        finalUrlPath,
                                        PACKAGE + packagePath + DOT + APIPATH + SPERATOR + BR)));
        JSONObject paths = jsonObject.getJSONObject("paths");
        //解析接口的method 信息
        Set<Map.Entry<String, Object>> pathEntitys = paths.entrySet();
        for (Map.Entry<String, Object> pathEntity : pathEntitys) {
            // Vehicle/cost/report/excel
            String path = pathEntity.getKey();
            JSONObject apiType = (JSONObject) pathEntity.getValue();
            Set<Map.Entry<String, Object>> entries1 = apiType.entrySet();
            String methodType = apiType.keySet().stream().findFirst().get();
            InterfaceMethodModel targetInterface = interfaceMap.get(((JSONArray) getObject((JSONObject) pathEntity.getValue(), methodType + ".tags")).get(0));
            MethodModel methodModel = new MethodModel();
            //请求参数导入。 返回参数导入
            targetInterface.addMethodModels(methodModel);
            methodModel.setHttpRout(path);
            for (Map.Entry<String, Object> stringObjectEntry : entries1) {
                // get put post delete
                String urlMethodType = stringObjectEntry.getKey();
                methodModel.setApiType(urlMethodType);
                JSONObject value = (JSONObject) stringObjectEntry.getValue();
                String methodDescription = value.getString("summary");
                methodModel.setDescription(methodDescription);
                String methodName = value.getString("operationId");
                methodModel.setMethodName(methodName);
                List<String> consumes = value.getJSONArray("consumes").toJavaList(String.class);
                List<String> produces = value.getJSONArray("produces").toJavaList(String.class);
                methodModel.setConsumes(consumes);
                methodModel.setProduces(produces);
                String returnName = (String) getObject(value, "responses.200.schema.ref");
                Optional<ContentsConvert> contentsConvert = ContentsConvert.ifContainGet(returnName);
                if (contentsConvert.isPresent()) {
                    methodModel.setReturnName(contentsConvert.get().name);
                    targetInterface.addPackage(contentsConvert.get().packageName);
                } else if (returnName == null) {
                    methodModel.setReturnName(VOID);
                } else {
                    String replace = returnName.replace("#/definitions/", "");
                    String temp = replace;
                    {
                        for (; ; ) {
                            int i = temp.indexOf("«");
                            if (i == -1) {
                                break;
                            }
                            String substring1 = temp.substring(0, i);
                            String modelp = modelpath.get(substring1);
                            if (modelp != null) {
                                targetInterface.addPackage(modelp);
                            }
                            ContentsImport.getValue(substring1).ifPresent(z -> targetInterface.addPackage(z.path));
                            temp = temp.substring(i + 1);
                        }
                    }
                    if (replace.contains("«")) {
                        String substring1 = replace.substring(replace.lastIndexOf("«") + 1, replace.indexOf("»"));
                        String modelp = modelpath.get(substring1);
                        if (modelp != null) {
                            targetInterface.addPackage(modelp);
                        }
                        ContentsImport.getValue(substring1).ifPresent(z -> targetInterface.addPackage(z.path));
                        String className1 = ModelClassType.getClassName(substring1);
                        replace = replace.replaceFirst(substring1, className1.trim());
                    } else {
                        String s = modelpath.get(replace);
                        if (s != null) {
                            targetInterface.addPackage(s);
                        }
                    }
                    methodModel.setReturnName(replace.replaceAll("»", ">").replaceAll("«", "<"));
                }
                //如果为get请求则通过list 进行遍历 使用TypeParam  规定post 所有请求参数只能在实体内
                JSONArray parameters = value.getJSONArray("parameters");
                if (parameters == null) {
                    continue;
                }
                if (RequestMethod.isGetMethod(urlMethodType)) {
                    methodModel.setParams(parameters.toJavaList(TypeParam.class));
                } else {
                    //有type 的
                    Map<String, List<Object>> type1 = parameters.parallelStream().collect(Collectors.groupingBy(k -> {
                                String type = ((JSONObject) k).getString("type");
                                if (type == null) {
                                    return "schema";
                                }
                                return "type";
                            })
                    );
                    if (type1.get("type") != null) {
                        List<TypeParam> type = toJavaList(TypeParam.class, type1.get("type"));
                        methodModel.setParams(type);
                    } else if (type1.get("schema") != null) {
                        List<SchemaParam> schema = toJavaList(SchemaParam.class, type1.get("schema"));
                        methodModel.setParamsObject(schema);
                        schema.forEach(item -> {
                            String schema1 = item.getSchema();
                            if (schema1.contains("List")) {
                                targetInterface.addPackage(ContentsImport.List.path);
                            }
                            String s = modelpath.get(schema1.replace("List<", "").replace(">", ""));
                            if (s != null) {
                                targetInterface.addPackage(s);
                            }
                        });
                    }
                }
            }
        }

        //实体写入文件中
        File fileEntity = new File(classPath + MODELPATH);
        File fileApi = new File(classPath + APIPATH);
        File serviceFile= new File(classPath + SERVICEPATH+"\\"+IMPLPATH);

        if (!fileApi.exists()) {
            fileApi.mkdirs();
        }
        if (!fileEntity.exists()) {
            fileEntity.mkdirs();
        }
        if (!serviceFile.exists()) {
            serviceFile.mkdirs();
        }
        for (Model model : models) {
            File file = new File(classPath + MODELPATH + "\\" + model.getModelName() + DOT + JAVA);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            StringBuilder modelString = new StringBuilder();
            //Package
            modelString.append(model.getPackagePath());
            //br
            modelString.append(BR);
            Set<String> importProperties = model.getImportProperties();
            //import
            importProperties.forEach(item -> {
                        String modelp = modelpath.get(item);
                        if (modelp != null) {
                            modelString.append(modelp);
                        }
                        ContentsImport.getValue(item).ifPresent(i -> modelString.append(i.path));
                    }
            );
            modelString.append(BR);
            //public class name{
            modelString.append("public class " + model.getModelName() + LEFT_BRACE + BR);
            model.getMemberModel().forEach(item -> {
                //描述
                if (item.getDescription() != null) {
                    modelString.append("\t/**\n"
                            + "\t*" + item.getDescription() + "\n"
                            + "\t**/\n");
                }
                //list 的处理方式
                if (ModelClassType.isList(item.getMemberType())) {
                    modelString.append(TAB + PRIVATE + item.getMemberType() + "<" + item.getItemName() + "> " + item.getMemberName() + SPERATOR);
                } else {
                    modelString.append(TAB + PRIVATE + item.getMemberType() + SPEACE + item.getMemberName() + SPERATOR);
                }
                modelString.append(BR);
            });
            modelString.append(BR);
            //get set
            for (MemberModel item : model.getMemberModel()) {
                //get
                if (ModelClassType.isList(item.getMemberType())) {
                    modelString.append(TAB + PRIVATE + item.getMemberType() + "<" + item.getItemName() + "> " + "get" + firstUpper(item.getMemberName()) + "()");
                } else {
                    modelString.append(TAB + PRIVATE + item.getMemberType() + SPEACE + "get" + firstUpper(item.getMemberName()) + "()");
                }
                modelString.append(LEFT_BRACE + BR);
                modelString.append(DOUBLE_TAB + "return " + "this." + item.getMemberName() + SPERATOR + BR);
                modelString.append(TAB + RIGHT_BRACE + DOUBLE_BR);
                //set
                if (ModelClassType.isList(item.getMemberType())) {
                    modelString.append(TAB + PRIVATE + VOID + "set" + firstUpper(item.getMemberName()) + "(" + item.getMemberType() + "<" + item.getItemName() + "> " + item.getMemberName() + ")");
                } else {
                    modelString.append(TAB + PRIVATE + VOID + "set" + firstUpper(item.getMemberName()) + "(" + item.getMemberType() + SPEACE + item.getMemberName() + ")");
                }
                modelString.append(LEFT_BRACE + BR);
                modelString.append(DOUBLE_TAB + "this." + item.getMemberName() + "=" + item.getMemberName() + SPERATOR + BR);
                modelString.append(TAB + RIGHT_BRACE + DOUBLE_BR);
            }
            modelString.append(RIGHT_BRACE);
            fileOutputStream.write(modelString.toString().getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        }

        //生成对应的接口
        Collection<InterfaceMethodModel> values = interfaceMap.values();
        //接口类地址
        Map<String, String> interfacepath = new HashMap();
        for (InterfaceMethodModel value : values) {
            //feign 接口路径
            String interfacePath = IMPORT+packagePath + DOT + APIPATH + DOT + getInterfaceName(value.getInterfaceName()) + SPERATOR;
            //fein 接口class 路径
            String interfaceClass = classPath + APIPATH + "\\" + getInterfaceName(value.getInterfaceName()) + DOT + JAVA;
            //service 接口路径
            String serviceInterfacePath = IMPORT+packagePath + DOT + SERVICEPATH + DOT + getInterfaceName(value.getInterfaceName())+"Service" + SPERATOR;
            //service 接口class 路径
            String serviceInterfaceClass = classPath + SERVICEPATH + "\\" + getInterfaceName(value.getInterfaceName())+"Service" + DOT + JAVA;
            //service impl 路径
            String serviceImplClass = classPath +SERVICEPATH+"\\"+ IMPLPATH + "\\" + getInterfaceName(value.getInterfaceName())+"ServiceImpl"  + DOT + JAVA;

            File file = new File(interfaceClass);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            File serviceInterfacefile = new File(serviceInterfaceClass);

            FileOutputStream serviceInterfaceOutputStream = new FileOutputStream(serviceInterfacefile);

            File serviceImplfile = new File(serviceImplClass);

            FileOutputStream serviceImplOutputStream = new FileOutputStream(serviceImplfile);

            StringBuilder interfaceString = new StringBuilder();
            //service class
            StringBuilder serviceInterfaceString = new StringBuilder();
            //serviceimpl class
            StringBuilder serviceImplString = new StringBuilder();
            //package
            interfaceString.append(value.getPackagePath());

            serviceInterfaceString.append(PACKAGE + packagePath + DOT + SERVICEPATH + SPERATOR + BR);

            serviceImplString.append(PACKAGE + packagePath+DOT+SERVICEPATH + DOT + IMPLPATH + SPERATOR + BR);
            //import
            //br
            interfaceString.append(BR);
            serviceInterfaceString.append(BR);
            serviceImplString.append(BR);
            Set<String> importProperties = value.getImportProperties();
            //import
            interfaceString.append("import feign.Param;\nimport feign.RequestLine;\nimport feign.Body;\n");
            //impl 需要打导入fegin和service 接口
            serviceImplString.append(serviceInterfacePath+BR);
            serviceImplString.append(interfacePath+BR);

            serviceImplString.append("import feign.Feign;"+BR+
                    "import org.springframework.beans.factory.annotation.Autowired;" +BR+
                    "import org.springframework.stereotype.Service;"+BR);
            importProperties.forEach(item -> {
                        interfaceString.append(item);
                        serviceInterfaceString.append(item);
                        serviceImplString.append(item);
                    }
            );
            //public class name {
            interfaceString.append("public interface " + getInterfaceName(value.getInterfaceName()) + LEFT_BRACE + BR);
            serviceImplString.append("@Service\n");
            serviceImplString.append("public class"+SPEACE+getInterfaceName(value.getInterfaceName())+"ServiceImpl implements"+SPEACE+getInterfaceName(value.getInterfaceName())+"Service" + LEFT_BRACE + BR);
            serviceImplString.append(TAB+"@Autowired" +BR+
                                     TAB+"private Feign.Builder feign;"+BR);
            serviceInterfaceString.append("public interface " + getInterfaceName(value.getInterfaceName())+"Service" + LEFT_BRACE + BR);
            value.getMethodModels().forEach(item -> {

                //@RequestLine() Get
                //
                StringJoiner urlJoiner = new StringJoiner("&", "?", "");
                StringJoiner bodyJoiner = new StringJoiner("", "({\"", "\"})");
                StringJoiner paramJoiner = new StringJoiner(",", "(", ")");
                StringJoiner paramNameJoiner = new StringJoiner(",", "(", ")");
                StringJoiner descrptionJoiner = new StringJoiner("*");
                //service 和service impl 参数
                StringJoiner interfaceparamJoiner = new StringJoiner(",", "(", ")");
                //@Param
                item.getParams().forEach(param -> {
                    urlJoiner.add(param.getName() + "=" + LEFT_BRACE + param.getName() + RIGHT_BRACE);
                    paramJoiner.add("@Param(\"" + param.getName() + "\") " + ModelClassType.getClassName(param.getType()) + SPEACE + param.getName());
                    interfaceparamJoiner.add(ModelClassType.getClassName(param.getType()) + SPEACE + param.getName());
                    paramNameJoiner.add(param.getName());
                    descrptionJoiner.add(param.getName() + "-->" + param.getDescription() + BR);
                });
                //post 请求
                item.getParamsObject().forEach(paramObject -> {
                    bodyJoiner.add(paramObject.getName());
                    paramJoiner.add("@Param(\"" + paramObject.getName() + "\") " + paramObject.getSchema() + SPEACE + paramObject.getName());
                    interfaceparamJoiner.add(paramObject.getSchema() + SPEACE + paramObject.getName());
                    paramNameJoiner.add(paramObject.getName());
                });
                //描述
                if (item.getDescription() != null) {
                    interfaceString.append("\t/**\n"
                            + "\t*" + item.getDescription() + "\n"
                            + "\t**/\n");
                }
                interfaceString.append(TAB + "@RequestLine(\"" + item.getApiType().toUpperCase() + " " + item.getHttpRout() + (urlJoiner.length() != 1 ? urlJoiner.toString() : "") + "\")" + BR);
                if (bodyJoiner.length() != 6) {
                    interfaceString.append(TAB + "@Body" + bodyJoiner.toString() + BR);
                }
                interfaceString.append(TAB + item.getReturnName() + SPEACE + item.getMethodName().substring(0, item.getMethodName().indexOf("Using")) + (paramJoiner.length() != 2 ? paramJoiner.toString() : "()") + SPERATOR + BR);
                serviceImplString.append(TAB+"@Override"+BR+TAB+"public"+SPEACE+ item.getReturnName() + SPEACE + item.getMethodName().substring(0, item.getMethodName().indexOf("Using")) + interfaceparamJoiner.toString() + LEFT_BRACE + BR);
                //调用feign
                serviceImplString.append(DOUBLE_TAB+"return feign.target("+getInterfaceName(value.getInterfaceName())+".class, \"\")."+item.getMethodName().substring(0, item.getMethodName().indexOf("Using"))+paramNameJoiner.toString()+SPERATOR+BR+TAB+RIGHT_BRACE+BR);
                serviceInterfaceString.append(TAB + item.getReturnName() + SPEACE + item.getMethodName().substring(0, item.getMethodName().indexOf("Using")) +  interfaceparamJoiner.toString()+ SPERATOR + BR);
            });
            interfaceString.append(RIGHT_BRACE);
            serviceImplString.append(RIGHT_BRACE);
            serviceInterfaceString.append(RIGHT_BRACE);
            fileOutputStream.write(interfaceString.toString().getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            serviceInterfaceOutputStream.write(serviceInterfaceString.toString().getBytes());
            serviceInterfaceOutputStream.flush();
            serviceInterfaceOutputStream.close();
            serviceImplOutputStream.write(serviceImplString.toString().getBytes());
            serviceImplOutputStream.flush();
            serviceImplOutputStream.close();
        }

    }

    public String getInterfaceName(String target) {
        String s = target.replaceFirst(String.valueOf(target.charAt(0)), String.valueOf((char) (target.charAt(0) - 32)));
        for (; ; ) {
            int i = s.indexOf("-");
            if (i == -1) {
                break;
            }
            s = s.replaceFirst(s.substring(i, i + 2), String.valueOf((char) (s.charAt(i + 1) - 32)));
        }
        return s;
    }

    public String firstUpper(String target) {
        char c = target.charAt(0);
        if (c >= 65 && c <= 122 && c <= 90) {
            return target;
        }
        return target.replaceFirst(String.valueOf(c), String.valueOf((char) (c - 32)));
    }

    public <T> List<T> toJavaList(Class<T> clazz, List<Object> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        List<T> result = new ArrayList<T>();
        list.forEach((item) -> {
            result.add(JSON.parseObject(((JSONObject) item).toJSONString(), clazz));
        });
        return result;
    }

    public Object getObject(JSONObject object, String getType) {
        AtomicReference<Object> result = new AtomicReference<Object>();
        AtomicReference<JSONObject> clone = new AtomicReference<>((JSONObject) object.clone());
        Splitter.on(".").split(getType).forEach(item -> {
                    Object o = clone.get().get(item);
                    result.set(o);
                    if (o instanceof JSONObject) {
                        clone.set((JSONObject) o);
                    }

                }
        );
        return result.get();
    }

    public static void main(String[] args) throws Exception {
        GenerateUtils generateUtils = new GenerateUtils("http://carapi.demo.ehi.com.cn/swagger/eHi.Car/v1", "com.example.demo.pa");
        generateUtils.generate();
    }
}
