package com.abo.exception;

import com.abo.util.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * @author : Abo
 * @date : 2022/1/21 22:19
 * @ControllerAdvice : Controller增强器,可对controller中被 @RequestMapping注解的方法加一些逻辑处理。
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @ExceptionHandler(Exception.class) : 配合@ControllerAdvice使用，一旦捕获到@ExceptionHandler指定的异常，就会调用该方法
     * @ResponseBody : 返回的数据格式(json)
     */
    @ApiOperation("指定出现异常调用的方法")
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult error(Exception e){
        e.printStackTrace();
        return ResponseResult.error().message("系统错误，请联系技术人员处理");
    }

    @ApiOperation("自定义异常")
    @ResponseBody
    @ExceptionHandler(SystemException.class)
    public ResponseResult error(SystemException e) {
        e.printStackTrace();
        return ResponseResult.error().code(e.getCode()).message(e.getMessage());
    }

    @ApiOperation("特定异常 —— 数字异常")
    @ResponseBody
    @ExceptionHandler(ArithmeticException.class)
    public ResponseResult error(ArithmeticException e) {
        e.printStackTrace();
        return ResponseResult.error().message("执行了ArithmeticException异常...");
    }

}
