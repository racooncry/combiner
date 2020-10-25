package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author yangxw
 * @Date 2020-10-23 上午9:28
 * @Description
 * @Version 1.0
 */
public class Server2 {


    public static void main(String[] args) {


        final int DEFAULT_PORT = 8888;
        ServerSocket serverSocket = null;
        final String QUIT = "quit";
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            while (true) {
                System.out.println("阻塞1");
                Socket socket = serverSocket.accept();
                System.out.println("阻塞2");
                System.out.println("客户端[" + socket.getPort() + "]已连接");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));


                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


                // 读取客户端发送的消息
                String readLine = null;

                while ((readLine = bufferedReader.readLine()) != null) {

                    System.out.println("客户端[" + socket.getPort() + "]:" + readLine);

                    // 回复客户发送的消息
                    bufferedWriter.write("服务器:" + readLine + "\n");
                    bufferedWriter.flush();
                    if (QUIT.equals(readLine)) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                    System.out.println("关闭serverSocket");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

}
