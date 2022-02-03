package com.abo.controller;

import com.abo.service.OssService;
import com.abo.util.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : Abo
 * @date : 2022/1/23 19:59
 */
@RestController
@RequestMapping("/oss/")
public class OssController {
    @Autowired
    private OssService ossService;

    @ApiOperation("图片上传")
    @PostMapping("/upload")
    public ResponseResult uploadOssFile(MultipartFile file) {
         return ossService.uploadFileAvatar(file);
    }
}
