package com.abo.controller;


import com.abo.service.EduSubjectService;
import com.abo.util.ResponseResult;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 * 影视科目 前端控制器
 * </p>
 *
 * @author Abo
 * @since 2022-01-24
 */
@Api("影片分类前端控制器")
@RestController
@RequestMapping("/edu-subject/")
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    @ApiOperation("excel批量导入数据")
    @PostMapping("/importExcel")
    public ResponseResult importExcel(MultipartFile file) {
        subjectService.batchImport(file, subjectService);
        return ResponseResult.success();
    }

    @ApiOperation("分类数据Tree")
    @GetMapping("getListTree")
    public ResponseResult listTree() {
        return ResponseResult.success().data("items", subjectService.listTree());
    }

    @ApiOperation("获取二级分类")
    @GetMapping("getSubjectTwoList")
    public ResponseResult getSubjectTwoList(@ApiParam(name = "pid", value = "分类父级id", required = true) @RequestParam String pid) {
        return ResponseResult.success().data("items", subjectService.getSubjectTwoList(pid));
    }

    @ApiOperation("Echarts图表")
    @GetMapping("echarts/{begin}/{end}")
    public ResponseResult echarts(@PathVariable String begin, @PathVariable String end) {
        Map<String, Object> map = subjectService.getEcharts(begin, end);
        return ResponseResult.success().data(map);
    }
}

