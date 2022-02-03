package com.abo.service;

import com.abo.entity.EduActor;
import com.abo.query.ActorQuery;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 演员 服务类
 * </p>
 *
 * @author Abo
 * @since 2022-01-21
 */
public interface EduActorService extends IService<EduActor> {

    /**
     * 分页
     * @param page 分页对象
     * @return 分页后的数据
     */
    Map<String, Object> getActorList(Page<EduActor> page);

    //重载
    void getActorList(Page<EduActor> page, ActorQuery actorQuery);

    IPage<EduActor> pageView(ActorQuery query);
}
