package com.abo.controller;


import com.abo.service.EduVideoService;
import com.abo.util.InitObject;
import com.abo.util.ResponseResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 影视视频 前端控制器
 * </p>
 *
 * @author Abo
 * @since 2022-01-29
 */
@RestController
@RequestMapping("/edu-video")
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @ApiOperation("视频上传")
    @PostMapping("/uploadAlyVideo")
    public ResponseResult uploadAlyVideo(MultipartFile file) {
        String id = videoService.uploadVideoAly(file);
        return ResponseResult.success().data("videoId", id);
    }

    @ApiOperation("根据id删除视频")
    @DeleteMapping("deleteVideo/{id}")
    public ResponseResult deleteVideo(@PathVariable String id) {
        videoService.deleteById(id);
        return ResponseResult.success();
    }

    @ApiOperation("批量删除视频")
    @DeleteMapping("deleteBatchVideo")
    public ResponseResult deleteBatchVideo(@RequestParam("videoList") List<String> videoList) {
        videoService.removeMoreAlyVideo(videoList);
        return ResponseResult.success();
    }

    @ApiOperation("播放视频")
    @RequestMapping("vo/{id}")
    public ModelAndView playVideo(@PathVariable String id) {
        try {
            //阿里云对象
            DefaultAcsClient client = InitObject.initCodClient("LTAI5t5xwsxMCSsU1YFMEPyE", "afoPZXKdsA9eOcCq3siiGKKuVLDPTn");
            //创建请求和相应的对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            //添加视频的id
            request.setVideoId(id);
            response = client.getAcsResponse(request);
            //授权码
            String playAuth = response.getPlayAuth();
            //把视频的id和授权码传到网页中播放视频
            Map<String, String> map = new HashMap<>();
            map.put("videoId", id);
            map.put("playAuth", playAuth);
            return new ModelAndView("show", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

