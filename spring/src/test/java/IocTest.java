import ioc.Bean;
import ioc.Bean2;
import ioc.Bean3;
import ioc.BeanFactory3;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IocTest {

    @Test
    public void testCutomScope() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        for (int i = 0; i < 10; i++) {
            ioc.custom.Bean bean = context.getBean("customBean", ioc.custom.Bean.class);
            System.out.println("tb:" + bean);
        }


        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ioc.custom.Bean bean = context.getBean("customBean", ioc.custom.Bean.class);
                    System.out.println("thread:" + bean);
                }
            }).start();

        }
    }

    @Test
    public void singleton() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        ioc.singleton.Bean2 sbean21_1 = context.getBean("sbean2", ioc.singleton.Bean2.class);
        System.out.println(sbean21_1);

        ioc.singleton.Bean2 sbean21_2 = context.getBean("sbean2", ioc.singleton.Bean2.class);
        System.out.println(sbean21_2);

        ioc.singleton.Bean1 sbean1 = context.getBean("sbean1", ioc.singleton.Bean1.class);
        System.out.println(sbean1);


    }


    @Test
    public void inct() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        ioc.inject.Bean injectBean = context.getBean("injectBean", ioc.inject.Bean.class);
        System.out.println(injectBean);
    }


    @Test
    public void test() {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Bean bean = context.getBean("bean1", Bean.class);
        System.out.println(bean);

        Bean2 bean2 = context.getBean("bean2", Bean2.class);
        System.out.println(bean2);


//        BeanFactory3 beanFactory3 = new BeanFactory3();
//        Bean3 bean3 = beanFactory3.getBean3();

        Bean3 bean3 = context.getBean("bean3", Bean3.class);
        System.out.println(bean3);


        Bean bean_1 = context.getBean("bean_1", Bean.class);
        Bean bean_2 = context.getBean("bean_2", Bean.class);
        Bean bean_3 = context.getBean("bean_3", Bean.class);

        System.out.println(bean_1);
        System.out.println(bean_2);
        System.out.println(bean_3);
    }


}
