import org.junit.jupiter.api.Test;
import util.TestUtils;
import webserver.connector.Request;
import webserver.processor.ServletProcessor;

import javax.servlet.Servlet;
import java.net.MalformedURLException;
import java.net.URLClassLoader;

/**
 * @Author yangxw
 * @Date 2020-10-31 下午4:53
 * @Description
 * @Version 1.0
 */
public class ProcessorTest {

    private static final String servletRequest = "GET /servlet/TimeServlet HTTP/1.1";

    @Test
    public void givenServletRequest_thenLoadServlet() throws MalformedURLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Request request = TestUtils.createRequest(servletRequest);
        ServletProcessor processor = new ServletProcessor();
        URLClassLoader loader = processor.getServletLoader();
        Servlet servlet = processor.getServlet(loader, request);
        System.out.println("servlet:" + servlet);
    }
}
