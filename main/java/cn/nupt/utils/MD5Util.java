package cn.nupt.utils;

import org.apache.commons.codec.digest.DigestUtils;
import sun.security.provider.MD5;

import javax.swing.*;

/**
 * md5加密
 *
 * @AUTHOR PizAn
 * @CREAET 2019-07-25 19:59
 */

public class MD5Util {
/*
    用户端：PASS=MD5(明文+固定Salt)
    服务端：PASS=MD5（用户输入+随机Salt）
*/

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    //固定salt
    public static final String salt = "1a2b3c4d";

    //第一次MD5
    public static String firstMd5(String input){

        String str = salt.charAt(0) + salt.charAt(2) + input + salt.charAt(5) + salt.charAt(8);
        return md5(str);
    }

    //第二次MD5，只不过这里用的的是随机的salt
    public static String secondMd5(String input,String salt){

        String str = salt.charAt(0) + salt.charAt(2) + input + salt.charAt(5) + salt.charAt(8);
        return md5(str);
    }


    //这里将上面两个结合一下，输入之后直接就输出结果
    public static  String mergrMd5(String input , String saltDB){
        return secondMd5(firstMd5(input),saltDB);
    }
}
