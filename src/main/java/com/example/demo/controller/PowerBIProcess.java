package com.example.demo.controller;


import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

public class PowerBIProcess {
    /**
     * 加密密钥
     */
    private static final String SHA256KEY = "3c6fa384648ffd5cf229ddf5ac82c480";

    /**
     * 源URL
     */
    private static final String URL = "http://powerbi.1hai.cn/";

    /**
     * @return String
     * @Description:生成秒值时间戳
     * @exception:
     * @author: 叶青青
     * @time:2018年12月4日 下午2:49:25
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * @param encryptString
     * @return String
     * @Description:加密字符串
     * @exception:
     * @author: 叶青青
     * @time:2018年12月4日 下午2:49:14
     */
    public static String getEncryptString(String encryptString) {
        String utfString = "";
        try {
            //utfString=hmacSha256(encryptString, SHA256KEY) ;
           utfString = URLEncoder.encode(hmacSha256(encryptString, SHA256KEY), "UTF-8");
           utfString = change(utfString);
        } catch (Exception e) {
        }
        return utfString;
    }

    /**
     * @param userNo
     * @param groupId
     * @param reportId
     * @return String
     * @Description:生成加密字符串
     * @exception:
     * @author: 叶青青
     * @time:2018年12月4日 下午2:48:47
     */
    public final String getUrl(String userNo, String groupId, String reportId) {
        String date = "1559703145";
        String decryptString = getString(groupId, reportId, userNo, "门店人员权限", date);
        String sign = getEncryptString(decryptString);
        return URL + "?" + decryptString + "&sign=" + sign;
    }

    /**
     * @param groupId
     * @param reportId
     * @param userNo
     * @param roles
     * @param time
     * @return String
     * @Description:拼接加密字符串
     * @exception:
     * @author: 叶青青
     * @time:2018年12月4日 下午2:48:26
     */
    public final String getString(String groupId, String reportId, String userNo, String roles, String time) {
        return "groupId=" + groupId + "&" + "reportId=" + reportId + "&reportUrlId=100001&" + "roles=" +
                roles + "&" + "time=" + time + "&upSysCode=ZJHTStore&" + "username=" + userNo;
    }

    /**
     * @param data 加密数据
     * @param key  密钥
     * @return String
     * @Description:使用HMAC SHA256创建base64哈希加密
     * @exception:
     * @author: 叶青青
     * @time:2018年12月4日 下午2:47:34
     */
    public static String hmacSha256(String data, String key) {
        Mac sha256Hmac;
        String hash = "";
        try {
            sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("ASCII"), "HmacSHA256");
            sha256Hmac.init(secretKey);
            String dataNew = key + data + key;
            hash = Base64.encodeBase64String(sha256Hmac.doFinal(dataNew.getBytes("ASCII")));
        } catch (NoSuchAlgorithmException e) {
        } catch (Exception e) {
        }
        return hash;
    }

    public static String change(String beforeMd5) {
        String[] uppercase = new String[0XFF + 1];
        String[] lowercase = new String[0XFF + 1];
        for (int i = 0; i <= 0XFF; i++) {
            uppercase[i] = "%" + String.format("%02x", i);
            lowercase[i] = uppercase[i];
            uppercase[i] = uppercase[i].toUpperCase();
        }

        beforeMd5 = StringUtils.replaceEach(beforeMd5, uppercase, lowercase);
        return beforeMd5;
    }

    public static void main(String[] args) {
        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
        System.out.println(encoder.encodeToString("sig=123123".getBytes()));
        System.out.println(Base64.encodeBase64String("sig=123123".getBytes()));
    }
}
