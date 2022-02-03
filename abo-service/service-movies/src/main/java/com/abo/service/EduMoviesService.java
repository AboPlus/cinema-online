package com.abo.service;

import com.abo.entity.EduMovies;
import com.abo.vo.MovieInfoVo;
import com.abo.vo.MoviePublishVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 影视 服务类
 * </p>
 *
 * @author Abo
 * @since 2022-01-24
 */
public interface EduMoviesService extends IService<EduMovies> {

    /** 添加影片基本信息 */
    String saveMovieInfo(MovieInfoVo courseInfoVo);

    /** 根据影片id获取影片基本信息 */
    MovieInfoVo getMovieInfo(String courseId);

    /** 修改影片基本信息 */
    void updateMovieInfo(MovieInfoVo courseInfoVo);

    /** 根据影片id发布 */
    MoviePublishVo publishMovieInfo(String id);

}
