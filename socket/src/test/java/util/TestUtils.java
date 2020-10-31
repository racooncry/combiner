package util;

import webserver.connector.Request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Author yangxw
 * @Date 2020-10-31 下午2:45
 * @Description
 * @Version 1.0
 */
public class TestUtils {

    public static Request createRequest(String requestStr) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestStr.getBytes());
        Request request = new Request(byteArrayInputStream);
        request.parse();
        return request;
    }


    public static String readFileToString(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));

    }


}
