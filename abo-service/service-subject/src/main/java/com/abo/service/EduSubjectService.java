package com.abo.service;

import com.abo.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 影视科目 服务类
 * </p>
 *
 * @author Abo
 * @since 2022-01-24
 */
public interface EduSubjectService extends IService<EduSubject> {
    /** 上传excel */
    void batchImport(MultipartFile file, EduSubjectService subjectService);

    /** 树形 */
    List<EduSubject> listTree();

    /** 根据创建的时间统计分析图表gmtCreate */
    Map<String,Object> getEcharts(String begin, String end);

    List<EduSubject> getSubjectTwoList(String pid);

}
