package com.abo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Abo
 * @date : 2022/1/24 13:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectReadDataDto {
    @ExcelProperty(value = "一级分类", index = 0)
    private String oneSubjectName;
    @ExcelProperty(value = "二级分类", index = 1)
    private String twoSubjectName;
}
