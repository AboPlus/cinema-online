package com.abo.service;

import com.abo.entity.EduChapter;
import com.abo.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 影视 服务类
 * </p>
 *
 * @author Abo
 * @since 2022-01-24
 */
public interface EduChapterService extends IService<EduChapter> {

    //根据影片id查询视频的列表
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //根据视频id删除
    boolean deleteChapter(String chapterId);
}
