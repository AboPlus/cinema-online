package com.abo.controller;

import com.abo.util.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * 登录控制器
 * @author : Abo
 * @date : 2022/1/23 12:25
 */
@RestController
@RequestMapping("/user/")
// @CrossOrigin //跨域,网关配置跨域后删除
public class ActorLoginController {
    @PostMapping("/login/")
    public ResponseResult login() {
        return ResponseResult.success().data("token", "admin");
    }

    @GetMapping("/info/")
    public ResponseResult info() {
        return ResponseResult.success()
                .data("roles", "[admin]")
                .data("name", "admin")
                .data("avatar", "xxx");
    }
}
