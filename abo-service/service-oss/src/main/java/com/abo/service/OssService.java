package com.abo.service;

import com.abo.util.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : Abo
 * @date : 2022/1/23 19:41
 */
public interface OssService {
    ResponseResult uploadFileAvatar(MultipartFile file);
}
