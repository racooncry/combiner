package com.shenfeng.yxw.utils.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yangxw
 * @Date 2020-09-21 下午3:57
 * @Description
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestStripDto {
    private double pLeftX;
    private double pLeftY;
    private double pRightX;
    private double pRightY;

}
