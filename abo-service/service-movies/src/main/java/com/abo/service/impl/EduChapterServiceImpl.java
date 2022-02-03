package com.abo.service.impl;

import com.abo.entity.EduChapter;
import com.abo.entity.EduVideo;
import com.abo.exception.SystemException;
import com.abo.mapper.EduChapterMapper;
import com.abo.service.EduChapterService;
import com.abo.service.EduVideoService;
import com.abo.vo.ChapterVo;
import com.abo.vo.VideoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 影视 服务实现类
 * </p>
 *
 * @author Abo
 * @since 2022-01-24
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据影片id，获取视频的片段数
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("movies_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //获取影视视频
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("movies_id", courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        //封装视频片段数据
        List<ChapterVo> finalList = new ArrayList<>();
        //遍历片段
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个片段
            EduChapter eduChapter = eduChapterList.get(i);
            //对拷到VO中
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            //添加到集合中
            finalList.add(chapterVo);


            //创建一个存储视频信息的集合
            ArrayList<VideoVo> videoList = new ArrayList<>();
            //封装视频
            for (int n = 0; n < eduVideoList.size(); n++) {
                //获取每个视频信息
                EduVideo eduVideo = eduVideoList.get(n);
                //判断视频和片段的外键是否一致
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    //封装到集合中
                    VideoVo videoVo = new VideoVo();
                    //对拷
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoList.add(videoVo);
                }
            }
            //把视频的信息列表添加到片段中
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        //需要判断：因为如果含有视频了，那么就不能删除了
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);

        //判断
        if (count > 0) {
            throw new SystemException(20001, "不能删除含有视频的片段");
        }
        return baseMapper.deleteById(chapterId) > 0;
    }
}
