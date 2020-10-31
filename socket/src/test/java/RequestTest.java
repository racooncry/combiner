import org.junit.jupiter.api.Test;
import webserver.connector.Request;

import java.io.ByteArrayInputStream;

/**
 * @Author yangxw
 * @Date 2020-10-31 下午1:45
 * @Description
 * @Version 1.0
 */
public class RequestTest {
    private static final String validRequest = "GET /index.html XX";

    @Test
    public void givenValidRequest() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(validRequest.getBytes());
        Request request = new Request(byteArrayInputStream);
        request.parse();
        String uri = request.getUri();
        System.out.println(uri);
    }


}
