package chat.bio.client;

import java.io.*;
import java.net.Socket;

public class ChatClient {
    final String DEFAULT_SERVER_HOST = "127.0.0.1";
    final int DEFAULT_SERVER_PORT = 8888;
    private final String QUIT = "quit";

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ChatClient() {
    }


    /**
     * 发送消息
     *
     * @param message
     * @throws IOException
     */
    public void send(String message) throws IOException {
        if (!socket.isOutputShutdown()) {
            writer.write(message + "\n");
            writer.flush();
        }
    }

    /**
     * 从服务器接收消息
     *
     * @throws IOException
     */
    public String receive() throws IOException {
        String message = null;
        if (!socket.isInputShutdown()) {
            message = reader.readLine();
        }
        return message;
    }

    public boolean readyToQuit(String message) {
        return QUIT.equals(message);
    }

    public void close() {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void start() {
        // 创建socket对象
        try {
            socket = new Socket(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);

            // 创建IO流
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 处理用户的输入
            new Thread(new UserInputHandler(this)).start();
            // 读取服务器转发的消息
            String message = null;
            while ((message = receive()) != null) {
                System.out.println(message);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }

    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.start();


    }


}
