package com.abo.test;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author : Abo
 * @date : 2022/1/29 18:38
 */
public class InitObject {
    public static DefaultAcsClient initCodClient(String accessKeyId, String accessKeySecret) {
        DefaultAcsClient client = null;
        try {
            String regionId = "cn-beijing";
            DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            client = new DefaultAcsClient(profile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }
}
