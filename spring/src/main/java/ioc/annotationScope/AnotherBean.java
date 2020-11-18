package ioc.annotationScope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Author yangxw
 * @Date 2020-11-18 下午12:20
 * @Description
 * @Version 1.0
 */
@Component
@Scope("prototype")
public class AnotherBean {
}
