package com.demo.lingsiojbackend.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

public class EncryptionUtil {

    /**
     * MD5加密
     * @param password 密码
     * @return 加密后的密码
     */
    public static String encryption(String password) {
        Digester md5 = new Digester(DigestAlgorithm.MD5);
        return md5.digestHex(password);
    }
}
