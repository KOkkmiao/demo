package com.example.demo.generate;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/08/19 17:43
 * desc :
 */
public class Contents {
    public static final String APIPATH ="api";
    public static final String JAVA ="java";
    public static final String MODELPATH = "entity";
    public static final String PACKAGE = "package ";
    public static final String IMPORT = "import ";
    public static final String SPERATOR = ";";
    public static final String DOT = ".";
    public static final String BR = "\n";
    public static final String DOUBLE_BR = "\n";
    public static final String TAB = "\t";
    public static final String DOUBLE_TAB = "\t\t";
    public static final String LEFT_BRACE = "{";
    public static final String RIGHT_BRACE = "}";
    public static final String SPEACE = " ";
    public static final String PUBLIC = "public ";
    public static final String PRIVATE = "private ";
    public static final String PROTECTED = "protected ";
    public static final String DEFAULT = " ";
    public static final String VOID = "void ";
    public static final String QUERY = "query";
    public static final String BODY = "body";
    // #/definitions/ 14
    public static final int REFLENGTH =14;
    public enum ContentsImport{
        List("import java.util.List;"+BR),
        BigDecimal("import java.math.BigDecimal;"+BR);
        public String path;

        /**
         * @param path
         */
        ContentsImport(String path) {
            this.path = path;
        }
        public static Optional<ContentsImport> getValue(String name){
            return Arrays.stream(values()).filter(item -> item.name().equals(name.trim())).findFirst();
        }
    }
}
