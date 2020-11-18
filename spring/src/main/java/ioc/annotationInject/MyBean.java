package ioc.annotationInject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author yangxw
 * @Date 2020-11-18 上午11:47
 * @Description
 * @Version 1.0
 */
@Component
public class MyBean {
    private AnotherBean anotherBean1;
    private AnotherBean anotherBean2;
    @Autowired
    private AnotherBean anotherBean3;

    private ApplicationContext context;

    public ApplicationContext getContext() {
        return context;
    }

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    private List<String> stringList;
    public Map<String, Integer> stringIntegerMap;


    private String string;

    public String getString() {
        return string;
    }

    @Override
    public String toString() {
        return "MyBean{" +
                "anotherBean1=" + anotherBean1 +
                ", anotherBean2=" + anotherBean2 +
                ", anotherBean3=" + anotherBean3 +
                ", stringList=" + stringList +
                ", stringIntegerMap=" + stringIntegerMap +
                ", string='" + string + '\'' +
                '}';
    }

    @Value("555")
    public void setString(String string) {
        this.string = string;
    }

    public Map<String, Integer> getStringIntegerMap() {
        return stringIntegerMap;
    }

    @Autowired
    public void setStringIntegerMap(Map<String, Integer> stringIntegerMap) {
        this.stringIntegerMap = stringIntegerMap;
    }

    public List<String> getStringList() {
        return stringList;
    }

    @Autowired
//    @Qualifier("stringList")
    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public AnotherBean getAnotherBean2() {
        return anotherBean2;
    }

    @Autowired
    public void setAnotherBean2(AnotherBean anotherBean2) {
        this.anotherBean2 = anotherBean2;
    }

    @Autowired
    public MyBean(AnotherBean anotherBean1) {
        this.anotherBean1 = anotherBean1;
    }

}
