package webserver;

import webserver.connector.Connector;
import webserver.connector.NioConnector;

/**
 * @Author yangxw
 * @Date 2020-10-31 下午3:34
 * @Description
 * @Version 1.0
 */
public class Bootstrap {
    public static void main(String[] args) {
//        Connector connector = new Connector();
        NioConnector connector = new NioConnector();

        connector.start();




    }


}
