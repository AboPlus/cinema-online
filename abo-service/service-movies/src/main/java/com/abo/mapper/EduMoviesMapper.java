package com.abo.mapper;

import com.abo.entity.EduMovies;
import com.abo.vo.MoviePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 影视 Mapper 接口
 * </p>
 *
 * @author Abo
 * @since 2022-01-24
 */
public interface EduMoviesMapper extends BaseMapper<EduMovies> {


    String sql = "SELECT ec.id,ec.title,ec.price,ec.video_num AS videoNum,ec.cover,\n" +
            "               et.name AS actorName,\n" +
            "               es1.title AS subjectLevelOne,\n" +
            "               es2.title AS subjectLevelTwo\n" +
            "        FROM edu_movies ec LEFT OUTER JOIN edu_movies_description ecd ON ec.id=ecd.id\n" +
            "                           LEFT OUTER JOIN edu_actor et ON ec.actor_id=et.id\n" +
            "                           LEFT OUTER JOIN edu_subject es1 ON ec.subject_parent_id=es1.id\n" +
            "                   LEFT OUTER JOIN edu_subject es2 ON ec.subject_id=es2.id\n" +
            "        WHERE ec.id=#{courseId}";

    /** 发布的影片 */
    @Select(sql)
    MoviePublishVo getPulishCourseInfo(String movieId);

}
