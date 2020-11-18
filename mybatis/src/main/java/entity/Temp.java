package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author yangxw
 * @Date 2020-11-18 下午3:55
 * @Description
 * @Version 1.0
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Temp {
    private Integer id;
    private String value1;
    private String value2;
}
