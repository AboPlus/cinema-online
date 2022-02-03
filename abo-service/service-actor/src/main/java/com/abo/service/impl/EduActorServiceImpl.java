package com.abo.service.impl;

import com.abo.entity.EduActor;
import com.abo.mapper.EduActorMapper;
import com.abo.query.ActorQuery;
import com.abo.service.EduActorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 演员 服务实现类
 * </p>
 *
 * @author Abo
 * @since 2022-01-21
 */
@Service
public class EduActorServiceImpl extends ServiceImpl<EduActorMapper, EduActor> implements EduActorService {

    @Override
    public Map<String, Object> getActorList(Page<EduActor> page) {
        // 分页条件
        QueryWrapper<EduActor> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        baseMapper.selectPage(page, wrapper);
        // 当前页、总的页数、每页记录数、总的记录数
        long current = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        long total = page.getTotal();
        // 记录数
        List<EduActor> records = page.getRecords();
        // 上一页、下一页
        boolean hasPrevious = page.hasPrevious();
        boolean hasNext = page.hasNext();

        // 添加分页条件
        Map<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasPrevious", hasPrevious);
        map.put("hasNext", hasNext);

        return map;
    }

    @Override
    public void getActorList(Page<EduActor> page, ActorQuery query) {
        //查询对象
        QueryWrapper<EduActor> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("sort");
        //添加查询的条件
        if (query == null) {
            baseMapper.selectPage(page, queryWrapper);
            return;
        }

        //判断
        if (StringUtils.isNotBlank(query.getName())) {
            queryWrapper.like("name", query.getName());
        }
        if (query.getLevel() != null) {
            queryWrapper.eq("level", query.getLevel());
        }
        if (StringUtils.isNotBlank(query.getBegin())) {
            queryWrapper.ge("gmt_create", query.getBegin());
        }
        if (StringUtils.isNotBlank(query.getEnd())) {
            queryWrapper.le("gmt_create", query.getEnd());
        }

        //添加条件到mapper
        baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public IPage<EduActor> pageView(ActorQuery query) {
        //查询对象
        QueryWrapper<EduActor> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(query.getName()), "name", query.getName());
        wrapper.eq(query.getLevel() != null && query.getLevel() != -1, "level", query.getLevel());
        wrapper.ge(StringUtils.isNotBlank(query.getBegin()), "gmt_create", query.getBegin());
        wrapper.le(StringUtils.isNotBlank(query.getEnd()), "gmt_create", query.getEnd());
        wrapper.orderByDesc("sort");

        Page<EduActor> page = new Page<>(query.getPage(), query.getLimit());
        baseMapper.selectPage(page, wrapper);
        return page;
    }
}
