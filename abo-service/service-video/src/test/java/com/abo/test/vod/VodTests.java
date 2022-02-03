package com.abo.test.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.List;

/**
 * 上传文档地址：https://help.aliyun.com/document_detail/53406.html
 * 服务文档地址：https://help.aliyun.com/document_detail/61065.html
 * @author : Abo
 * @date : 2022/1/29 19:55
 */
public class VodTests {

    public static final String accessKeyId = "LTAI5t5xwsxMCSsU1YFMEPyE";
    public static final String accessKeySecret = "afoPZXKdsA9eOcCq3siiGKKuVLDPTn";

    //填入AccessKey信息
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-beijing";  // 点播服务接入地域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * 获取音/视频上传地址和凭证
     *
     * @param client 发送请求客户端
     * @return CreateUploadVideoResponse 获取音/视频上传地址和凭证响应数据
     * @throws Exception
     */
    public static CreateUploadVideoResponse createUploadVideo(DefaultAcsClient client) throws Exception {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle("abo");
        request.setFileName("C:/data/test01.mp4");

        //UserData，用户自定义设置参数，用户需要单独回调URL及数据透传时设置(非必须)
        //JSONObject userData = new JSONObject();

        //UserData回调部分设置
        //消息回调设置，指定时以此为准，否则以全局设置的事件通知为准
        //JSONObject messageCallback = new JSONObject();
        //设置回调地址
        //messageCallback.put("CallbackURL", "http://192.168.0.1/16");
        //设置回调方式，默认为http
        //messageCallback.put("CallbackType", "http");
        //userData.put("MessageCallback", messageCallback.toJSONString());

        //UserData透传数据部分设置
        //用户自定义的扩展字段，用于回调时透传返回
        //JSONObject extend = new JSONObject();
        //extend.put("MyId", "user-defined-id");
        //userData.put("Extend", extend.toJSONString());

        //request.setUserData(userData.toJSONString());

        return client.getAcsResponse(request);
    }

    public static void uploadVideo() throws ClientException {
        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
        CreateUploadVideoResponse response = new CreateUploadVideoResponse();
        try {
            response = createUploadVideo(client);
            System.out.print("VideoId = " + response.getVideoId() + "\n");
            System.out.print("UploadAddress = " + response.getUploadAddress() + "\n");
            System.out.print("UploadAuth = " + response.getUploadAuth() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    public static void getVideoInfo() throws ClientException {
        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
        GetVideoInfoResponse response = new GetVideoInfoResponse();
        try {

            GetVideoInfoRequest request = new GetVideoInfoRequest();
            request.setVideoId("0d431d983ee54e0d93ef17f0eccd9958");
            response = client.getAcsResponse(request);

            System.out.print("Url = " + response.getVideo().getCoverURL() + "\n");
            System.out.print("Title = " + response.getVideo().getTitle() + "\n");
            System.out.print("Description = " + response.getVideo().getDescription() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    public static void getVideoUrl() throws ClientException {
        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            GetPlayInfoRequest request = new GetPlayInfoRequest();
            request.setVideoId("23390ac2c545440da822fcf87c5bb24e");
            response = client.getAcsResponse(request);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    public static void refreshVideoUrl() throws ClientException {
        DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
        RefreshUploadVideoResponse response = new RefreshUploadVideoResponse();
        try {
            RefreshUploadVideoRequest request = new RefreshUploadVideoRequest();
            //音/视频ID
            request.setVideoId("23390ac2c545440da822fcf87c5bb24e");
            response = client.getAcsResponse(request);
            System.out.print("UploadAddress = " + response.getUploadAddress() + "\n");
            System.out.print("UploadAuth = " + response.getUploadAuth() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    public static void upload() throws ClientException {
        // 一、视频文件上传
        // 视频标题(必选)
        String title = "测试标题";
        // 1.本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        // 2.网络流上传时，文件名称为源文件名，如文件名称.mp4(必选)。
        // 任何上传方式文件名必须包含扩展名
        String fileName = "C:/data/test01.mp4";
        // 本地文件上传
        testUploadVideo(accessKeyId, accessKeySecret, title, fileName);
    }

    private static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        request.setStorageLocation("outin-171630d580ec11ecb5cc00163e0eb78b.oss-cn-beijing.aliyuncs.com");
        request.setApiRegionId("cn-beijing");
        /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
        //request.setEnableCheckpoint(false);
        /* OSS慢请求日志打印超时时间，是指每个分片上传时间超过该阈值时会打印debug日志，如果想屏蔽此日志，请调整该阈值。单位: 毫秒，默认为300000毫秒*/
        //request.setSlowRequestsThreshold(300000L);
        /* 可指定每个分片慢请求时打印日志的时间阈值，默认为300s*/
        //request.setSlowRequestsThreshold(300000L);
        /* 是否显示水印(可选)，指定模板组ID时，根据模板组配置确定是否显示水印*/
        //request.setIsShowWaterMark(true);
        /* 自定义消息回调设置(可选)，参数说明请参见请求参数说明。*/
        // request.setUserData("{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://example.aliyundoc.com\"}}");
        /* 视频分类ID(可选) */
        //request.setCateId(0);
        /* 视频标签,多个用逗号分隔(可选) */
        //request.setTags("标签1,标签2");
        /* 视频描述(可选) */
        //request.setDescription("视频描述");
        /* 封面图片(可选) */
        //request.setCoverURL("http://example.aliyundoc.com/image_01.jpg");
        /* 模板组ID(可选) */
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56****");
        /* 工作流ID(可选) */
        //request.setWorkflowId("d4430d07361f0*be1339577859b0****");
        /* 存储区域(可选) */
        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        /* 开启默认上传进度回调 */
        //request.setPrintProgress(false);
        /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
        //request.setProgressListener(new PutObjectProgressListener());
        /* 设置您实现的生成STS信息的接口实现类*/
        // request.setVoDRefreshSTSTokenListener(new RefreshSTSTokenImpl());
        /* 设置应用ID*/
        //request.setAppId("app-1000000");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    // 请求示例
    public static void main(String[] argv) throws ClientException {
        upload();
    }
}
