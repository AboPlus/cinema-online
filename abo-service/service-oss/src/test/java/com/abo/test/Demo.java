package com.abo.test;

import java.util.UUID;
import java.util.zip.CRC32;

/**
 * @author : Abo
 * @date : 2022/1/23 19:48
 */
public class Demo {
    public static void main(String[] args) {
        CRC32 crc32 = new CRC32();
        crc32.update(UUID.randomUUID().toString().getBytes());
        System.out.println(crc32.getValue());
    }
}
