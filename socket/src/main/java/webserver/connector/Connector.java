package webserver.connector;

import webserver.processor.ServletProcessor;
import webserver.processor.StaticProcess;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author yangxw
 * @Date 2020-10-31 下午3:27
 * @Description
 * @Version 1.0
 */
public class Connector implements Runnable {
    private static final int DEFAULT_PORT = 8888;
    private ServerSocket server;

    private int port;

    public Connector() {
        this(DEFAULT_PORT);
    }

    public Connector(int port) {
        this.port = port;
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            System.out.println("启动服务器，监听端口：" + port);
            while (true) {
                Socket socket = server.accept();

                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                Request request = new Request(inputStream);
                request.parse();
                System.out.println("uri: " + request.getUri());


                Response response = new Response(outputStream);
                response.setRequest(request);


                if (request.getUri().startsWith("/servlet/")) {
                    ServletProcessor processor = new ServletProcessor();
                    processor.process(request, response);
                } else {
                    StaticProcess staticProcess = new StaticProcess();
                    staticProcess.process(request, response);
                }


                close(socket);


            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(server);
        }
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
