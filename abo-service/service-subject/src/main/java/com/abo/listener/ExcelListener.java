package com.abo.listener;

import com.abo.dto.SubjectReadDataDto;
import com.abo.entity.EduSubject;
import com.abo.service.EduSubjectService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import java.util.Map;

/**
 * excel监听器
 *
 * @author : Abo
 * @date : 2022/1/24 13:07
 */
public class ExcelListener extends AnalysisEventListener<SubjectReadDataDto> {

    /** 通过构造方法注入eduSubjectService */
    private EduSubjectService subjectService;

    public ExcelListener() {
    }

    public ExcelListener(EduSubjectService eduSubjectService) {
        this.subjectService = eduSubjectService;
    }

    /** 读取excel数据 */
    @Override
    public void invoke(SubjectReadDataDto subjectReadDataDto, AnalysisContext analysisContext) {
        //判断
        if (subjectReadDataDto == null) {
            throw new RuntimeException("添加数据失败");
        }

        //添加一级分类
        EduSubject existOneSubject = existOneSubject(subjectService, subjectReadDataDto.getOneSubjectName());
        if (existOneSubject == null) {
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(subjectReadDataDto.getOneSubjectName());
            existOneSubject.setParentId("0");
            subjectService.save(existOneSubject);
        }

        //获取一级分类的id
        String pid = existOneSubject.getId();

        //添加二级分类
        EduSubject existTwoSubject = existTwoSubject(subjectService, subjectReadDataDto.getTwoSubjectName(), pid);
        if (existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(subjectReadDataDto.getTwoSubjectName());
            existTwoSubject.setParentId(pid);
            subjectService.save(existTwoSubject);
        }
    }

    /** 读取头信息 */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头:" + headMap);
    }

    /** 读取后进行的操作 */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    /** 判断一级分类是否重复 */
    private EduSubject existOneSubject(EduSubjectService subjectService, String name) {
        LambdaQueryWrapper<EduSubject> wrapper = new LambdaQueryWrapper<EduSubject>()
                .eq(EduSubject::getTitle, name)
                .eq(EduSubject::getParentId, "0");
        return subjectService.getOne(wrapper);
    }

    /** 判断二级分类是否重复 */
    private EduSubject existTwoSubject(EduSubjectService subjectService, String name, String pid) {
        LambdaQueryWrapper<EduSubject> wrapper = new LambdaQueryWrapper<EduSubject>()
                .eq(EduSubject::getTitle, name)
                .eq(EduSubject::getParentId, pid);
        return subjectService.getOne(wrapper);
    }
}
