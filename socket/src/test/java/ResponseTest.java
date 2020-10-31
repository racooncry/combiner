import org.junit.jupiter.api.Test;
import util.TestUtils;
import webserver.connector.ConnectorUtils;
import webserver.connector.Request;
import webserver.connector.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @Author yangxw
 * @Date 2020-10-31 下午1:45
 * @Description
 * @Version 1.0
 */
public class ResponseTest {

    private static final String validRequest = "GET /index.html HTTP/1.1";
    private static final String invalidRequest = "GET /notfound.html HTTP/1.1";


    private static final String status200 = "HTTP/1. 200 OK\r\n\r\n";
    private static final String status404 = "HTTP/1. 404 File Not Found\r\n\r\n";


    @Test
    public void givenValidRequestThenValidStaticResource() throws IOException {
        Request request = TestUtils.createRequest(validRequest);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Response response = new Response(out);
        response.setRequest(request);
        response.sendStaticResource();


        String resource = TestUtils.readFileToString(ConnectorUtils.WEB_ROOT + request.getUri());
        System.out.println(status200 + resource);
        System.out.println(out.toString());
    }

    @Test
    public void givenValidRequestThenValidError() throws IOException {
        Request request = TestUtils.createRequest(invalidRequest);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Response response = new Response(out);
        response.setRequest(request);
        response.sendStaticResource();


        String resource = TestUtils.readFileToString(ConnectorUtils.WEB_ROOT + "\\404.html");
        System.out.println(status404 + resource);
        System.out.println(out.toString());
    }

}
