package com.abo.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : Abo
 * @date : 2022/1/24 16:05
 */
@Api("影片发布信息")
@Data
public class MoviePublishVo {
    @ApiModelProperty("影片id")
    private String id;

    @ApiModelProperty("影片名称")
    private String title;

    @ApiModelProperty("影片的总段数")
    private Integer videoNum;

    @ApiModelProperty("影片封面海报")
    private String cover;

    @ApiModelProperty("演员一级头衔")
    private String subjectLevelOne;

    @ApiModelProperty("演员二级头衔")
    private String subjectLevelTwo;

    @ApiModelProperty("主演名称")
    private String actorName;

    @ApiModelProperty("影片价格，用于显示")
    private String price;
}
