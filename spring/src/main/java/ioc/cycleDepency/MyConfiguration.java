package ioc.cycleDepency;

import ioc.annotation.Bean1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yangxw
 * @Date 2020-11-18 上午11:09
 * @Description
 * @Version 1.0
 */
@Configuration
@ComponentScan(value = "ioc.cycleDepency")
public class MyConfiguration {

}
