package com.abo.controller;


import com.abo.entity.EduVideo;
import com.abo.service.EduVideoService;
import com.abo.util.ResponseResult;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 影视视频 前端控制器
 * </p>
 *
 * @author Abo
 * @since 2022-01-24
 */
@RestController
@RequestMapping("/edu-video-movie")
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @ApiOperation("保存视频描述信息")
    @PostMapping("addVideo")
    public ResponseResult addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return ResponseResult.success();
    }

    @ApiOperation("更新视频描述信息")
    @PutMapping("updateVideo")
    public ResponseResult updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return ResponseResult.success();
    }

    @ApiOperation("删除视频描述")
    @DeleteMapping("deleteVideo/{id}")
    public ResponseResult deleteVideo(@PathVariable String id){
        videoService.removeById(id);
        return ResponseResult.success();
    }

    @ApiOperation("视频id")
    @GetMapping("videoById/{id}")
    public ResponseResult videoById(@PathVariable String id){
        EduVideo video = videoService.getById(id);
        return ResponseResult.success().data("vsid",video.getVideoSourceId());
    }
}

