package com.example.demo.generate;

/**
 * @author 35716 <a href="xiaopeng.miao@1hai.cn">Contact me.</a>
 * @version 1.0
 * @since 2020/08/19 17:43
 * desc :
 */
public class Contents {
    public static final String SPERATOR = ";";
    public static final String PACKAGE = "package ";
    public static final String IMPORT = "import ";
    public static final String LISTPATH = "import java.util.List;";

    public static final String MODELPATH = "model";
    public static final String DOT = ".";
    public static final String APIPATH ="api";
    // #/definitions/ 14
    public static final int REFLENGTH =14;
    public enum ContentsImport{
        List("import java.util.List;");
        private String path;

        /**
         * @param path
         */
        ContentsImport(String path) {
            this.path = path;
        }
    }
}
