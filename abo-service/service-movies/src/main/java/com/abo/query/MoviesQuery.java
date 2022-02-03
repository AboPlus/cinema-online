package com.abo.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Abo
 * @date : 2022/1/29 22:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MoviesQuery extends BaseQuery{
    private String title;
    private String status;
}
