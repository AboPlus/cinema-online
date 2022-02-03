package com.abo.service;

import com.abo.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 影视视频 服务类
 * </p>
 *
 * @author Abo
 * @since 2022-01-29
 */
public interface EduVideoService {

    /** 视频上传到阿里云：视频点播 */
    String uploadVideoAly(MultipartFile file);

    /** 删除阿里云中的视频文件 */
    void removeMoreAlyVideo(List videoList);

    /** 按照id删除视频文件 */
    void deleteById(String videoId);
}
