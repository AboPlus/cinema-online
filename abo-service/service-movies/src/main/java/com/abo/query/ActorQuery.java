package com.abo.query;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Abo
 * @date : 2022/1/24 16:06
 */
@EqualsAndHashCode(callSuper = true)
@Api("演员查询实体")
@Data
public class ActorQuery extends BaseQuery{
    @ApiModelProperty("演员名称，用于like查询")
    private String name;

    @ApiModelProperty("演员的头衔，1表示一线演员，2表示二线演员")
    private Integer level;

    @ApiModelProperty(value = "上映时间",example = "2020-12-25 10:10:00")
    private String begin;

    @ApiModelProperty(value = "上映时间",example = "2021-02-25 10:10:00")
    private String end;
}
