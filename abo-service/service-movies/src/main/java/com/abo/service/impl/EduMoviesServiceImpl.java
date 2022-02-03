package com.abo.service.impl;

import com.abo.entity.EduMovies;
import com.abo.entity.EduMoviesDescription;
import com.abo.exception.SystemException;
import com.abo.mapper.EduMoviesMapper;
import com.abo.service.EduMoviesDescriptionService;
import com.abo.service.EduMoviesService;
import com.abo.vo.MovieInfoVo;
import com.abo.vo.MoviePublishVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 影视 服务实现类
 * </p>
 *
 * @author Abo
 * @since 2022-01-24
 */
@Service
public class EduMoviesServiceImpl extends ServiceImpl<EduMoviesMapper, EduMovies> implements EduMoviesService {

    @Autowired
    private EduMoviesDescriptionService eduMoviesDescriptionService;

    @Override
    public String saveMovieInfo(MovieInfoVo movieInfoVo) {
        //1.添加影片基本信息 2.需要把vo的数据转换影片的bean：MovieInfoVo--EduMovies(entity)
        EduMovies eduMovies = new EduMovies();
        // 按照相同的属性名称属性copy
        BeanUtils.copyProperties(movieInfoVo, eduMovies);
        int rows = baseMapper.insert(eduMovies);
        //判断是否保存成功
        if (rows == 0) {
            throw new SystemException(20001, "影片添加失败");
        }
        //获取添加后的影片id
        String moviesId = eduMovies.getId();

        //添加影片简介(edu_movies_description)
        EduMoviesDescription eduMoviesDescription = new EduMoviesDescription();
        eduMoviesDescription.setDescription(movieInfoVo.getDescription());
        eduMoviesDescription.setId(moviesId);
        eduMoviesDescriptionService.save(eduMoviesDescription);

        //返回影片的id
        return moviesId;
    }

    @Override
    public MovieInfoVo getMovieInfo(String movieId) {
        EduMovies eduMovies = baseMapper.selectById(movieId);
        //影片信息的vo
        MovieInfoVo movieInfoVo = new MovieInfoVo();
        //完成对拷
        BeanUtils.copyProperties(eduMovies, movieInfoVo);
        //获取影片的简介信息
        EduMoviesDescription moviesDescription = eduMoviesDescriptionService.getById(movieId);
        movieInfoVo.setDescription(moviesDescription.getDescription());

        return movieInfoVo;
    }

    @Override
    public void updateMovieInfo(MovieInfoVo movieInfoVo) {
        //创建影片的实体
        EduMovies eduMovies = new EduMovies();
        //对拷
        BeanUtils.copyProperties(movieInfoVo, eduMovies);

        //添加一个修改日期
        eduMovies.setGmtModified(new Date());
        int update = baseMapper.updateById(eduMovies);

        //判断更新是否成功
        if (update == 0) {
            throw new SystemException(20001, "影片信息更新失败");
        }

        //修改影片简介信息
        EduMoviesDescription moviesDescription = new EduMoviesDescription();
        moviesDescription.setId(movieInfoVo.getId());
        moviesDescription.setDescription(movieInfoVo.getDescription());
        moviesDescription.setGmtModified(new Date());
        eduMoviesDescriptionService.updateById(moviesDescription);
    }

    @Override
    public MoviePublishVo publishMovieInfo(String id) {
        return baseMapper.getPulishCourseInfo(id);
    }
}
