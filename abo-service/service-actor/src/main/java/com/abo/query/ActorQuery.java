package com.abo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Abo
 * @date : 2022/1/21 22:04
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "actor查询对象",description = "把演员的查询条件封装")
@Data
public class ActorQuery extends BaseQuery{
    @ApiModelProperty(value = "演员的名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔，1表示一线")
    private Integer level;

    @ApiModelProperty(value = "开始查询时间",example = "2020-02-25 14:19:02")
    private String begin;

    @ApiModelProperty(value = "结束查询时间",example = "2020-12-25 14:19:02")
    private String end;
}
