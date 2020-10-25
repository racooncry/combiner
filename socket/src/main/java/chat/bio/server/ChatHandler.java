package chat.bio.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatHandler implements Runnable {

    private ChatServer chatServer;
    private Socket socket;

    public ChatHandler(ChatServer chatServer, Socket socket) {
        this.chatServer = chatServer;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // 存储新上线用户
            chatServer.addClients(socket);
            // 读取用户发送的信息
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = null;
            while ((message = reader.readLine()) != null) {
                String forwardMessage = "客户端【" + socket.getPort() + "】:" + message + "\n";
                System.out.print(forwardMessage);
                // 将消息转发给聊天室的在线的其他成员
                chatServer.forwardMessage(socket, forwardMessage + "\n");


                // 检查用户是否准备退出
                if (chatServer.readyQuit(message)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                chatServer.removeClient(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
