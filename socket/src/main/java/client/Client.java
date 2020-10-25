package client;

import java.io.*;
import java.net.Socket;

/**
 * @Author yangxw
 * @Date 2020-10-23 上午9:28
 * @Description
 * @Version 1.0
 */
public class Client {

    public static void main(String[] args) {


        final String DEFAULT_SERVER_HOST = "127.0.0.1";
        final int DEFAULT_SERVER_PORT = 8888;
        Socket socket = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        // 创建socket
        try {
            socket = new Socket(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);

            // 创建I/O流
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 等待用户输入信息
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            String input = console.readLine();

            writer.write(input + "\n");
            writer.flush();


            // 读取服务器返回的消息
            String msg = reader.readLine();
            System.out.println(msg);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                    System.out.println("关闭socket");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
