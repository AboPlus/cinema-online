package com.abo.controller;


import com.abo.entity.EduMovies;
import com.abo.query.MoviesQuery;
import com.abo.service.EduMoviesService;
import com.abo.util.ResponseResult;
import com.abo.vo.MovieInfoVo;
import com.abo.vo.MoviePublishVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 影视 前端控制器
 * </p>
 *
 * @author Abo
 * @since 2022-01-24
 */
@RestController
@RequestMapping("/edu-movies")
public class EduMoviesController {
    @Autowired
    private EduMoviesService moviesService;

    @ApiOperation("影片信息分页")
    @PostMapping("getMoviesList")
    public ResponseResult getMoviesList(@RequestBody(required = false) MoviesQuery query) {
        //分页
        Page<EduMovies> moviesPage = new Page<>(query.getPage(), query.getLimit());
        //查询条件
        QueryWrapper<EduMovies> wrapper = new QueryWrapper<>();
        if (query.getTitle() != null) {
            wrapper.like("title", query.getTitle());
        }
        if (query.getStatus() != null && !Objects.equals(query.getStatus(), "-1")) {
            wrapper.like("status", query.getStatus());
        }

        //添加查询条件
        moviesService.page(moviesPage, wrapper);
        //记录数
        long total = moviesPage.getTotal();
        //分页数据
        List<EduMovies> list = moviesPage.getRecords();

        return ResponseResult.success().data("total", total).data("list", list);
    }

    // @ApiOperation("影片信息分页")
    // @PostMapping("getMoviesList/{current}/{limit}")
    // public ResponseResult getMoviesList(@PathVariable long current, @PathVariable long limit,
    //                                     @RequestBody(required = false) MoviesQuery moviesQuery) {
    //     //分页
    //     Page<EduMovies> moviesPage = new Page<>(current, limit);
    //     //查询条件
    //     QueryWrapper<EduMovies> wrapper = new QueryWrapper<>();
    //     if (moviesQuery.getTitle() != null) {
    //         wrapper.like("title", moviesQuery.getTitle());
    //     }
    //     if (moviesQuery.getStatus() != null) {
    //         wrapper.like("status", moviesQuery.getStatus());
    //     }
    //
    //     //添加查询条件
    //     moviesService.page(moviesPage, wrapper);
    //     //记录数
    //     long total = moviesPage.getTotal();
    //     //分页数据
    //     List<EduMovies> list = moviesPage.getRecords();
    //
    //     return ResponseResult.success().data("total", total).data("list", list);
    // }

    @ApiOperation("根据id删除影片")
    @DeleteMapping("deleteMovies/{id}")
    public ResponseResult deleteMovies(@PathVariable String id) {
        moviesService.removeById(id);
        return ResponseResult.success();
    }

    @ApiOperation("添加影片基本信息")
    @PostMapping("addMoviesInfo")//只要post对@RequestBody有效
    public ResponseResult addMoviesInfo(@RequestBody MovieInfoVo movieInfoVo) {
        //添加后返回影片id
        String id = moviesService.saveMovieInfo(movieInfoVo);
        return ResponseResult.success().data("moviesId", id);
    }

    @ApiOperation("根据影片id获取基本信息")
    @GetMapping("getMoviesInfo/{moviesId}")
    public ResponseResult getMoviesInfo(@PathVariable String moviesId) {
        MovieInfoVo courseInfo = moviesService.getMovieInfo(moviesId);
        return ResponseResult.success().data("courseInfoVo", courseInfo);
    }

    @ApiOperation("更新影片信息")
    @PutMapping("updateMoviesInfo")
    public ResponseResult updateMoviesInfo(@RequestBody MovieInfoVo movieInfoVo) {
        moviesService.updateMovieInfo(movieInfoVo);
        return ResponseResult.success();
    }

    @ApiOperation("根据影片id获取发布影片信息")
    @GetMapping("getPublishMoviesInfo")
    public ResponseResult getPublishMoviesInfo(@RequestParam("id") String id) {
        MoviePublishVo publishVo = moviesService.publishMovieInfo(id);
        return ResponseResult.success().data("publishVo", publishVo);
    }

    @ApiOperation("发布影片，修改status的状态")
    @PostMapping("publishMovies")
    public ResponseResult publishMovies(@RequestBody Map<String, String> params) {
        EduMovies eduMovies = new EduMovies();
        eduMovies.setId(params.get("id"));
        eduMovies.setStatus("Normal");
        moviesService.updateById(eduMovies);

        return ResponseResult.success();
    }
}

