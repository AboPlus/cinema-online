package com.abo.controller;


import com.abo.entity.EduChapter;
import com.abo.service.EduChapterService;
import com.abo.util.ResponseResult;
import com.abo.vo.ChapterVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 影视 前端控制器
 * </p>
 *
 * @author Abo
 * @since 2022-01-24
 */
@RestController
@RequestMapping("/edu-chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    @ApiOperation("根据id获取片段信息")
    @GetMapping("getChapterVideo/{courseId}")
    public ResponseResult getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return ResponseResult.success().data("allChapterVideo", list);
    }

    @ApiOperation("添加影片片段信息")
    @PostMapping("/addChapter")
    public ResponseResult addChapter(@RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return ResponseResult.success();
    }

    @ApiOperation("根据id获取视频信息")
    @GetMapping("getChapterInfo/{chapterId}")
    public ResponseResult getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return ResponseResult.success().data("chapter", eduChapter);
    }

    @ApiOperation("修改视频片段信息")
    @PutMapping("updateChapter")
    public ResponseResult updateChapter(@RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return ResponseResult.success();
    }

    @ApiOperation("删除视频片段信息")
    @DeleteMapping("deleteChapter/{chapterId}")
    public ResponseResult deleteChapter(@PathVariable String chapterId) {
        return chapterService.deleteChapter(chapterId) ? ResponseResult.success() : ResponseResult.error();
    }
}

