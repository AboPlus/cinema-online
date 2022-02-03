package com.abo.query;

import lombok.Data;

/**
 * @author : Abo
 * @date : 2022/1/23 22:17
 */
@Data
public class BaseQuery {
    private Integer page = 1;
    private Integer limit = 10;
}
