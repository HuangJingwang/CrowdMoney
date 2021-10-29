package com.example.util;

import com.example.Constant.CrowdConstant;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CrowdUtil {
    /**
     * 对明文字符串进行 MD5 加密
     * @param source 传入的明文字符串
     * @return 加密结果
     */
    public static String md5(String source) {
// 1.判断 source 是否有效
        if(source == null || source.length() == 0) {
// 2.如果不是有效的字符串抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        try {
// 3.获取 MessageDigest 对象
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
// 4.获取明文字符串对应的字节数组
            byte[] input = source.getBytes();
// 5.执行加密
            byte[] output = messageDigest.digest(input);
// 6.创建 BigInteger 对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);
// 7.按照 16 进制将 bigInteger 的值转换为字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();
            return encoded;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
