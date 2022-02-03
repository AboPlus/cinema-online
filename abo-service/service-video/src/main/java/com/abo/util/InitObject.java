package com.abo.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author : Abo
 * @date : 2022/1/29 19:19
 */
public class InitObject {
    public static DefaultAcsClient initCodClient(String accessKeyId, String accessKeySecret) {
        //客户端
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
