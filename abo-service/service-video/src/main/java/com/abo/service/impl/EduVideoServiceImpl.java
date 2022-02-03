package com.abo.service.impl;

import com.abo.exception.SystemException;
import com.abo.service.EduVideoService;
import com.abo.util.ConstantPropertiesUtils;
import com.abo.util.InitObject;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 影视视频 服务实现类
 * </p>
 *
 * @author Abo
 * @since 2022-01-29
 */
@Service
public class EduVideoServiceImpl implements EduVideoService {

    @Override
    public String uploadVideoAly(MultipartFile file) {
        //获取视频名称
        String filename = file.getOriginalFilename();
        //视频上传后的名称
        String title = filename.substring(0, filename.lastIndexOf("."));
        //上传后的视频id
        String videoId = null;

        //完成上传
        try {
            //获取输入流
            InputStream inputStream = file.getInputStream();
            //阿里云请求对象
            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET, title, filename, inputStream);
            request.setApiRegionId("cn-beijing");

            //阿里云响应对象
            UploadVideoImpl uploadVideo = new UploadVideoImpl();
            UploadStreamResponse response = uploadVideo.uploadStream(request);

            //判断上传是否成功
            videoId = response.getVideoId();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return videoId;
    }

    @Override
    public void removeMoreAlyVideo(List videoList) {
        //获取阿里云对象客户端
        DefaultAcsClient client = InitObject.initCodClient(ConstantPropertiesUtils.ACCESS_KEY_ID,
                ConstantPropertiesUtils.ACCESS_KEY_SECRET);

        //创建阿里云请求对象
        DeleteVideoRequest request = new DeleteVideoRequest();
        //按照视频的id删除，需要把数据转为指定格式：1，2，3
        String videoIds = StringUtils.join(videoList.toArray(), ",");

        //向请求删除对象添加
        request.setVideoIds(videoIds);

        //删除
        try {
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new SystemException(20001, "视频删除失败");
        }
    }

    @Override
    public void deleteById(String videoId) {
        //获取阿里云对象客户端
        DefaultAcsClient client = InitObject.initCodClient(ConstantPropertiesUtils.ACCESS_KEY_ID,
                ConstantPropertiesUtils.ACCESS_KEY_SECRET);

        //创建阿里云请求对象
        DeleteVideoRequest request = new DeleteVideoRequest();

        //向请求删除对象添加
        request.setVideoIds(videoId);

        //删除
        try {
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new SystemException(20001, "视频删除失败");
        }
    }
}
