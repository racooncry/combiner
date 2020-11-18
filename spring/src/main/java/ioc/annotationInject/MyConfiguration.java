package ioc.annotationInject;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yangxw
 * @Date 2020-11-18 上午11:48
 * @Description
 * @Version 1.0
 */
@ComponentScan("ioc.annotationInject")
@Configuration
public class MyConfiguration {

//    @Bean("stringList")
//    public List<String> stringList() {
//        List<String> list = new ArrayList<>();
//        list.add("1");
//        list.add("2");
//        return list;
//    }


    //    @Bean
//    public Map<String,Integer> integerMap(){
//        HashMap<String, Integer> map = new HashMap<>();
//        map.put("111",222);
//        map.put("222",333);
//        return map;
//    }
    @Bean("int1")
    public Integer integer1() {
        return 111;
    }

    @Bean("int2")
    public Integer integer2() {
        return 222;
    }


    @Bean
    @Order(56)
    public String string1() {
        return "333";
    }

    @Bean
    @Order(12)
    public String string2() {
        return "444";
    }


}
