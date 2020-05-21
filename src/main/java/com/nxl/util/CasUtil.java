package com.nxl.util;

import org.apache.shiro.crypto.hash.ConfigurableHashService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;

import java.util.UUID;

public class CasUtil {

    public static String digestEncodedPassword (String encodedPass, String dynaSalt, Long numberOfIterations) {
        ConfigurableHashService hashService = new DefaultHashService();
        hashService.setHashAlgorithmName("MD5");

        hashService.setHashIterations(numberOfIterations.intValue());

        final HashRequest request = new HashRequest.Builder()
                .setSalt(dynaSalt)
                .setSource(encodedPass)
                .build();
        return hashService.computeHash(request).toHex();
    }

    public static void main(String[] args) {
        String salt = UUID.randomUUID().toString().replace("-", "");
        System.out.println("salt:"+salt);
        String password = digestEncodedPassword("123456", salt , 2L);
        System.out.println(password);
    }
}
