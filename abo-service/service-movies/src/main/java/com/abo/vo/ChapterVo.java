package com.abo.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频节数
 * @author : Abo
 * @date : 2022/1/29 16:38
 */
@Data
public class ChapterVo {
    private String id;
    private String title ;
    private List<VideoVo> children = new ArrayList<>();
}
