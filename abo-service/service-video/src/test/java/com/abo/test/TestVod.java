package com.abo.test;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

/**
 * @author : Abo
 * @date : 2022/1/29 18:42
 */
public class TestVod {
    //阿里云的id
    static String id = "LTAI5t5xwsxMCSsU1YFMEPyE";
    //阿里云秘钥
    static String key = "afoPZXKdsA9eOcCq3siiGKKuVLDPTn";
    //阿里云的视频id
    static String videoId = "0d431d983ee54e0d93ef17f0eccd9958";

    //根据视频id获取播放凭证
    public static void getPlayAuth() {
        try {
            //获取客户端
            DefaultAcsClient client = InitObject.initCodClient(id, key);

            //创建请求和相应对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            //把视频的id添加到请求对象中
            request.setVideoId(videoId);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            System.out.println("PlayAuth=" + response.getPlayAuth());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //根据id获取视频的播放地址
    public static void getPlayUrl() {
        try {
            //获取客户端
            DefaultAcsClient client = InitObject.initCodClient(id, key);

            //创建请求和相应对象
            GetPlayInfoRequest request = new GetPlayInfoRequest();
            GetPlayInfoResponse response = new GetPlayInfoResponse();

            //把视频的id添加到请求对象中
            request.setVideoId(videoId);
            response = client.getAcsResponse(request);

            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //获取视频的播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.println("url=" + playInfo.getPlayURL() + "\n");
            }
            //获取基本信息
            System.out.println("title=" + response.getVideoBase().getTitle() + "\n");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //视频上传
    public static void uploadVideo() {
        //视频上传后的名称
        String title = "abo";
        //上传视频的地址
        String fileName = "C:/data/123.mp4";

        //上传
        UploadVideoRequest request = new UploadVideoRequest(id, key, title, fileName);
        //如果视频大可以分片上传
        request.setPartSize(2 * 1024 * 1024L);//2MB
        //指定并发线程数
        request.setTaskNum(1);
        request.setApiRegionId("cn-beijing");

        UploadVideoImpl uploadVideo = new UploadVideoImpl();
        UploadVideoResponse response = uploadVideo.uploadVideo(request);

        //判断
        if (response.isSuccess()) {
            System.out.println("videoId=" + response.getVideoId());
        } else {
            //返回错误码和错误信息
            System.out.println("errorCode=" + response.getCode() + response.getMessage());
        }
    }


    public static void main(String[] args) {
        uploadVideo();
    }
}
