import ioc.Bean;
import ioc.Bean2;
import ioc.Bean3;
import ioc.annotation.Bean1;
import ioc.annotation.MyConfiguration;
import ioc.annotation.MyConfiguration2;
import ioc.annotationInject.AnotherBean;
import ioc.annotationInject.MyBean;
import ioc.annotationScope.TestBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IocTest {


    @Test
    public void testAnnotationInit() {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(ioc.annotationInit.TestConfiguration.class);

        ioc.annotationInit.TestBean testBean1 = context.getBean("testBean1", ioc.annotationInit.TestBean.class);

        System.out.println(testBean1);

        context.close();
    }

    @Test
    public void testAnnotationLazy() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ioc.annotationLazy.TestConfiguration.class);
        System.out.println("context init");

        ioc.annotationLazy.TestBean testBean1 = context.getBean("testBean1", ioc.annotationLazy.TestBean.class);
        ioc.annotationLazy.TestBean testBean2 = context.getBean("testBean2", ioc.annotationLazy.TestBean.class);
        System.out.println(testBean1);
        System.out.println(testBean2);
    }

    @Test
    public void scopeAnnotation() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ioc.annotationScope.TestConfiguration.class);
//        for (int i = 0; i < 10; i++) {
//            TestBean bean1 = context.getBean("testBean1", TestBean.class);
//            System.out.println(bean1);
//        }

//        for (int i = 0; i < 10; i++) {
//            TestBean bean2 = context.getBean("testBean2", TestBean.class);
//            System.out.println(bean2);
//
//        }

        for (int i = 0; i < 10; i++) {
            TestBean bean2 = context.getBean("testBean2", TestBean.class);
            bean2.printerAnotherBean();

        }
    }


    @Test
    public void annotation3() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ioc.annotationInject.MyConfiguration.class);
        MyBean bean1 = context.getBean("myBean", MyBean.class);
        for (String s : bean1.getStringList()) {
            System.out.println(s);
        }
        System.out.println(bean1);

        AnotherBean anotherBean = bean1.getContext().getBean("anotherBean", AnotherBean.class);
        System.out.println(anotherBean);
    }


    @Test
    public void annotation2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration2.class);
        Bean1 bean1 = context.getBean("bean2", Bean1.class);
        System.out.println(bean1);
    }

    @Test
    public void annotation() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfiguration.class);
        Bean1 bean1 = context.getBean("bean3", Bean1.class);
        System.out.println(bean1);
    }

    @Test
    public void extendBeanNoParent() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        ioc.extendBean.Class1NoParent class1 = context.getBean("class1NoParent", ioc.extendBean.Class1NoParent.class);
        ioc.extendBean.Class2NoParent class2 = context.getBean("class2NoParent", ioc.extendBean.Class2NoParent.class);
        System.out.println(class1);
        System.out.println(class2);
    }

    @Test
    public void extendBean() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        ioc.extendBean.Class1 class1 = context.getBean("class1", ioc.extendBean.Class1.class);
        ioc.extendBean.Class2 class2 = context.getBean("class1", ioc.extendBean.Class2.class);
        System.out.println(class1);
        System.out.println(class2);
    }


    @Test
    public void initBean() {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        ioc.init.Bean lazyBean = context.getBean("initBean", ioc.init.Bean.class);
        System.out.println(lazyBean);
        context.close();
    }


    @Test
    public void lazy() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        System.out.println("context已经被创建" + context);
        ioc.lazy.Bean lazyBean = context.getBean("lazyBean", ioc.lazy.Bean.class);
        System.out.println(lazyBean);

    }


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
