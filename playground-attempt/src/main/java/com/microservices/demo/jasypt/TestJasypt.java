package com.microservices.demo.jasypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;

public class TestJasypt {
    public static void main(String[] args) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword("Demo_Pwd!2020");
        standardPBEStringEncryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        standardPBEStringEncryptor.setIvGenerator(new RandomIvGenerator());
        String result = standardPBEStringEncryptor.encrypt("springCloud_Pwd!");
        System.out.println(result);
        System.out.println(standardPBEStringEncryptor.decrypt(result));
        System.out.println("Old password Decryption : " + standardPBEStringEncryptor.decrypt("o+Ei3+fudsNcFfSj/5lTSLvwgOcb1cYUyz8m+lEndMJfKS2Jiuy+q4DTA6cMcZDwYSApWM5MS+zXba5tNgCjNA=="));
    }
}
