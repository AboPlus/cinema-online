package com.abo.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author : Abo
 * @date : 2022/1/24 16:03
 */
@Api("影片描述信息")
@Data
public class MovieInfoVo {
    @ApiModelProperty("影片id")
    private String id;

    @ApiModelProperty("演员的id")
    private String actorId;

    @ApiModelProperty("影片分类id")
    private String subjectId;

    @ApiModelProperty("影片一级分类id")
    private String subjectParentId;

    @ApiModelProperty("影片名称")
    private String title;

    @ApiModelProperty("影片价格，设置0表示免费")
    private BigDecimal price;

    @ApiModelProperty("影片的总段数")
    private Integer videoNum;

    @ApiModelProperty("影片封面海报")
    private String cover;

    @ApiModelProperty("影片简介")
    private String description;
}
