package com.crowdfunding.farming.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author Jiang-gege
 * 2020/9/2412:56
 */
public class CheckUtil {
    private static final String token = "7200";

    public  boolean validParams(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        //排序
        Arrays.sort(arr);
        //生成字符串
        StringBuffer sb = new StringBuffer();
       for (String a :arr){
           sb.append(a);
    }
       //shal加密
        String formattedText = shal(sb.toString());
       if(formattedText.equals(signature)){
       return true;
       }
       return false;
    }

    /**
     * 将字符串进行sha1加密
     *
     * @param str 需要加密的字符串
     * @return 加密后的内容
     */
    public  String shal(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}