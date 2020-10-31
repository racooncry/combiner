package webserver.processor;

import webserver.connector.Request;
import webserver.connector.Response;

import java.io.IOException;

/**
 * @Author yangxw
 * @Date 2020-10-30 上午9:38
 * @Description
 * @Version 1.0
 */
public class StaticProcess {

    public void process(Request request, Response response) {

        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
