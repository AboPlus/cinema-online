package com.abo.service.impl;

import com.abo.dto.SubjectReadDataDto;
import com.abo.entity.EduSubject;
import com.abo.listener.ExcelListener;
import com.abo.mapper.EduSubjectMapper;
import com.abo.service.EduSubjectService;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 影视科目 服务实现类
 * </p>
 *
 * @author Abo
 * @since 2022-01-24
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Override
    public void batchImport(MultipartFile file, EduSubjectService subjectService) {
        try {
            // 文件输入流
            InputStream inputStream = file.getInputStream();
            // 需要指定读取哪个Class，读取默认sheet,文件流自动关闭
            EasyExcel.read(inputStream, SubjectReadDataDto.class, new ExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<EduSubject> listTree() {
        //获取所有分类数据
        List<EduSubject> entities = baseMapper.selectList(null);
        //找到一级分类
        return entities.stream().filter(categoryEntity -> categoryEntity.getParentId().equals("0"))
                .map(menu -> menu.setChildren(getChildren(menu, entities)))
                .sorted((m1, m2) -> (m1.getSort() == null ? 0 : m1.getSort()) - (m2.getSort() == null ? 0 : m2.getSort()))
                .collect(Collectors.toList());
    }

    /** 编写一个读取树形的递归方法 */
    private List<EduSubject> getChildren(EduSubject root, List<EduSubject> all) {
        //递归查找
        List<EduSubject> list = all.stream().filter(categoryEntity -> {
            //找子节点
            return categoryEntity.getParentId().equals(root.getId());
        }).map(categoryEntity -> {
            //子节点
            categoryEntity.setChildren(getChildren(categoryEntity, all));
            return categoryEntity;
        }).sorted((m1, m2) -> {
            return (m1.getSort() == null ? 0 : m1.getSort()) - (m2.getSort() == null ? 0 : m2.getSort());
        }).collect(Collectors.toList());

        //返回子节点集合
        return list;
    }

    @Override
    public Map<String, Object> getEcharts(String begin, String end) {
        //创建集合
        Map<String, Object> map = new HashMap<>();
        //查询条件
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        //数据
        List<EduSubject> data = baseMapper.selectList(wrapper);
        //把数据添加到list集合中
        Stream<String> stringStream = data.stream().map(e -> e.getTitle());
        List<String> collect = stringStream.collect(Collectors.toList());
        //存储数值
        List<Integer> nums = new ArrayList<>();

        //根据data获取的影片数量
        for (EduSubject datum : data) {
            QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("parent_id",datum.getId());
            wrapper1.between("gmt_create",begin,end);
            List<EduSubject> list = baseMapper.selectList(wrapper1);
            nums.add(list.size());
        }
        //把两个集合添加到map中
        map.put("data",collect);
        map.put("nums",nums);

        return map;
    }

    @Override
    public List<EduSubject> getSubjectTwoList(String pid) {
        return baseMapper.selectList(new LambdaQueryWrapper<EduSubject>().eq(EduSubject::getParentId, pid));
    }
}
