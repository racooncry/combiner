package chat.bio.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 负责等待用户的输入消息，并且发送给服务端
 */
public class UserInputHandler implements Runnable {

    private ChatClient chatClient;

    public UserInputHandler(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @Override
    public void run() {

        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                String input = consoleReader.readLine();
                // 服务器发送消息
                chatClient.send(input);
                if (chatClient.readyToQuit(input)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
