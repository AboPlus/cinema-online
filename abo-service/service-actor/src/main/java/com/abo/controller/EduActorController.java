package com.abo.controller;


import com.abo.entity.EduActor;
import com.abo.query.ActorQuery;
import com.abo.service.EduActorService;
import com.abo.util.ResponseResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 演员 前端控制器
 * </p>
 *
 * @author Abo
 * @since 2022-01-21
 */
@RestController
@RequestMapping("/edu-actor/")
// @CrossOrigin // 网关配置跨域后删除
public class EduActorController {
    @Autowired
    private EduActorService actorService;

    @ApiOperation("分页查询演员信息")
    @GetMapping("/page/")
    public ResponseResult page(@ApiParam(name = "actorQuery", value = "查询对象", required = false) ActorQuery query) {
        IPage<EduActor> page = actorService.pageView(query);
        return ResponseResult.success().data("rows", page.getRecords()).data("total", page.getTotal());
    }

    @ApiOperation("保存演员信息")
    @PostMapping("/save")
    public ResponseResult save(@RequestBody EduActor actor) {
        actorService.saveOrUpdate(actor);
        return ResponseResult.success();
    }

    @ApiOperation("逻辑删除演员信息")
    @DeleteMapping("/remove")
    public ResponseResult remove(@ApiParam(name = "id", value = "演员的主键", required = true) String id) {
        actorService.removeById(id);
        return ResponseResult.success();
    }

    //////////////////////////////////////////////////////////////////////////////////////

    @ApiOperation("逻辑删除演员信息")
    @DeleteMapping("/delete/{id}")
    public ResponseResult delete(@ApiParam(name = "id", value = "演员的主键", required = true)
                                 @PathVariable("id") String id) {
        actorService.removeById(id);
        return ResponseResult.success();
    }

    @ApiOperation("查询演员信息")
    @GetMapping("/list")
    public ResponseResult list() {
        return ResponseResult.success().data("items", actorService.list(null));
    }

    @ApiOperation("分页查询演员信息")
    @GetMapping("/{page}/{limit}")
    public ResponseResult page(@ApiParam(name = "page", value = "当前页码", required = true)
                               @PathVariable("page") long page,
                               @ApiParam(name = "limit", value = "每页显示的记录数", required = true)
                               @PathVariable("limit") long limit,
                               @ApiParam(name = "actorQuery", value = "查询对象", required = false) ActorQuery actorQuery) {
        Page<EduActor> actorPage = new Page<>(page, limit);
        // actorService.getActorList(actorPage);
        actorService.getActorList(actorPage, actorQuery);

        List<EduActor> records = actorPage.getRecords();
        long total = actorPage.getTotal();
        // 返回
        return ResponseResult.success().data("total", total).data("rows", records);
    }
}

