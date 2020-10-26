package chat.nio.client;


import org.springframework.util.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * @Author yangxw
 * @Date 2020-10-26 上午8:43
 * @Description
 * @Version 1.0
 */
public class ChatClient {
    private static final String DEFAULT_SERVER_HOST = "127.0.0.1";
    private static final int DEFAULT_SERVER_PORT = 8888;
    private static final String QUIT = "quit";
    private static final int BUFFER = 1024;

    private Selector selector;
    private ByteBuffer rBuffer = ByteBuffer.allocate(BUFFER);
    private ByteBuffer wBuffer = ByteBuffer.allocate(BUFFER);
    private Charset charset = Charset.forName("UTF-8");
    private String host;
    private int port;
    private SocketChannel client;


    public ChatClient() {
        this(DEFAULT_SERVER_HOST, DEFAULT_SERVER_PORT);
    }

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    private void start() {
        try {
            client = SocketChannel.open();
            client.configureBlocking(false);
            selector = Selector.open();

            client.register(selector, SelectionKey.OP_CONNECT);
            client.connect(new InetSocketAddress(host, port));

            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    handles(key);
                }
                keys.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClosedSelectorException closedSelectorException) {
            // 用户正常退出
        } finally {
            close(selector);
        }
    }

    private void handles(SelectionKey key) throws IOException {
        // Connect事件 - 连接就绪事件
        if (key.isConnectable()) {
            SocketChannel client = (SocketChannel) key.channel();
            if (client.isConnectionPending()) {
                client.finishConnect();
                // 处理用户的输入
                new Thread(new UserInputHandler(this)).start();
            }
            client.register(selector, SelectionKey.OP_READ);
        }
        // READ事件
        else if (key.isReadable()) {
            SocketChannel client = (SocketChannel) key.channel();
            String message = receive(client);
            if (StringUtils.isEmpty(message)) {
                // 服务器异常
                close(selector);
            } else {
                System.out.println(message);
            }
        }

    }

    public void send(String message) throws IOException {
        if (StringUtils.isEmpty(message)) {
            return;
        }
        wBuffer.clear();
        wBuffer.put(charset.encode(message));
        wBuffer.flip();
        while (wBuffer.hasRemaining()) {
            client.write(wBuffer);
        }

        // 检查用户是否准备退出
        if (readyToQuit(message)) {
            close(selector);
        }
    }

    private String receive(SocketChannel client) throws IOException {
        rBuffer.clear();
        while (client.read(rBuffer) > 0) ;
        rBuffer.flip();
        return String.valueOf(charset.decode(rBuffer));

    }


    public boolean readyToQuit(String msg) {
        return QUIT.equals(msg);
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


    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.start();
    }
}
