package com.knowlesys.kwm;

/**
 * Copyright © 2017-present, Knowlesys, Inc.
 * All rights reserved.
 * <p>
 * Created by hfx on 2018-05-09.
 */

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

/**
 * 密码加密工具
 * 算法：
 * 1、MD5密码
 * 2、前面加5个随机数
 * 3、后面加长度为5随机字符串
 * 4、整体Base64
 * 6、翻转字符串
 * @author Knowlesys
 */
public class PwdEncoder {

    /**
     * 得到加密后的密码（重点在密码其余都是干扰项）
     *
     * @param password 密码
     * @return 用户登录的加密后的字符串
     */
    public static String encoder(String password){
        PwdEncoder pwdEncoder = new PwdEncoder();
        String encoderStr = pwdEncoder.getRandomNum() + pwdEncoder.getMd5(password) + pwdEncoder.getRandomString();
        String str = pwdEncoder.getBase64(encoderStr);
        StringBuilder builder = new StringBuilder(str);
        str = builder.reverse().toString();
        return str;
    }


    /**
     * 获取5位随机数
     * @return 5为随机数
     */
    public String getRandomNum() {
        StringBuffer sbf = new StringBuffer();
        Random random = new Random();
        for(int i = 0; i < 5; i++) {
            int num = random.nextInt(10);
            sbf.append(num);
        }
        return sbf.toString();
    }

    /**
     * 获取5位随机字符串，使用Java UUID类
     *
     * @return 返回5位随机字符串
     */
    public String getRandomString() {
        return UUID.randomUUID().toString().substring(0, 5).toUpperCase();
    }


    /**
     * 获取传入字符串的BASE64编码
     * @param str 要BASE64的字符串
     * @return BASE64之后的字符串
     */
    public String getBase64(String str) {
        //如果Eclipse中此处导入BASE64Encoder出错，
        //需要重新配置buildpath，把JRE删掉，然后再加进来
        String base64 = new BASE64Encoder().encode(str.getBytes());
        return base64;
    }


    /**
     * MD5 摘要算法
     * @param pwd 密码字符串
     * @return MD5后的字符串
     */
    public String getMd5(String pwd) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = pwd.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}