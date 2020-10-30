package chat.aio.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Author yangxw
 * @Date 2020-10-30 上午9:10
 * @Description
 * @Version 1.0
 */
public class UserInputHandler implements Runnable {
    private ChatClient chatClient;

    public UserInputHandler(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public void run() {
        try {
            //等待用户输入消息
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String input = br.readLine();
                //向服务器发送消息
                chatClient.send(input);
                //检查用户是否准备退出
                if (chatClient.readyToQuit(input)) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
