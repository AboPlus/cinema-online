package com.abo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Abo
 * @date : 2022/1/21 22:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemException extends RuntimeException{
    /** 异常码 */
    private Integer code;
    /** 异常信息 */
    private String msg;
}
