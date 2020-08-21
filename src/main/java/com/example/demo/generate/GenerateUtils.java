package com.example.demo.generate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
            this.classPath = System.getProperty("user.dir") + classPath.replaceAll(".", "\\") + "\\entity";
            packagePath = classPath;
        }
        this.url = url;

    }

    public void generate() throws Exception {
        //获取swagger 信息
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(new Request.Builder().get().url(url).build());
        Response execute = call.execute();
        String respones = execute.body().string();
        if (!execute.message().equals("OK")) {
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
        for (Map.Entry<String, Object> entry : entries) {
            String key = entry.getKey();
            if (key.contains("《")) {
                continue;
            }
            Model model = new Model();
            models.add(model);
            String rootPath = packagePath + MODELPATH + DOT + key + SPERATOR;
            //将实体的路径和名称 存放到
            modelpath.put(key, IMPORT + rootPath);
            model.setModelName(key);
            //实体的包路径
            model.setPackagePath(PACKAGE + packagePath + MODELPATH + SPERATOR);
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
                String type = ModelClassType.getClassName(value.getString("type"));
                //需要导入list 包
                importProperties.add(type);
                memberMo.setMemberType(type);
                if (ModelClassType.isArray(type)) {
                    String itemName = ((String) getObject(value, "items.$ref")).substring(REFLENGTH);
                    memberMo.setItemName(itemName);
                    //需要导入对应实体的包
                    importProperties.add(itemName);
                }
                //description 描述
                String description = value.getString("description");
                memberMo.setDescription(description == null ? "" : description);
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
                                        PACKAGE + packagePath + APIPATH + SPERATOR)));
        JSONObject paths = jsonObject.getJSONObject("paths");
        //解析接口的method 信息
        Set<Map.Entry<String, Object>> pathEntitys = paths.entrySet();
        for (Map.Entry<String, Object> pathEntity : pathEntitys) {
            // Vehicle/cost/report/excel
            String path = pathEntity.getKey();
            JSONObject apiType = (JSONObject) pathEntity.getValue();
            Set<Map.Entry<String, Object>> entries1 = apiType.entrySet();
            InterfaceMethodModel targetInterface = interfaceMap.get(getObject((JSONObject) pathEntity.getValue(), "get.tags"));
            MethodModel methodModel = new MethodModel();
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
                String returnName = (String) getObject(value, "responses.200.schema.$ref");
                Optional<ContentsConvert> contentsConvert = ContentsConvert.ifContainGet(returnName);
                if (contentsConvert.isPresent()) {
                    methodModel.setReturnName(contentsConvert.get().name);
                } else {
                    methodModel.setReturnName(returnName);
                }
                //如果为get请求则通过list 进行遍历 使用TypeParam  规定post 所有请求参数只能在实体内
                if (RequestMethod.isGetMethod(urlMethodType)) {
                    JSONArray parameters = value.getJSONArray("parameters");
                    methodModel.setParams(parameters.toJavaList(MethodModel.TypeParam.class));
                }else{

                }
            }
        }


    }

    public Object getObject(JSONObject object, String getType) {
        AtomicReference<Object> result = null;
        AtomicReference<JSONObject> clone = new AtomicReference<>((JSONObject) object.clone());
        Splitter.on(".").split(getType).forEach(item -> {
                    Object o = clone.get().get(item);
                    result.set(object.get(item));
                    if (o instanceof JSONObject) {
                        clone.set((JSONObject) o);
                    }

                }
        );
        return result.get();
    }

    public static void main(String[] args) {
        //
        String urlPath = "http://java2.demo.ehi.com.cn/Reporting-System/v2/api-docs";
        String substring = urlPath.substring(0, urlPath.lastIndexOf("/"));
        urlPath = substring.substring(0, substring.lastIndexOf("/"));
        System.out.println(urlPath);
    }
}
