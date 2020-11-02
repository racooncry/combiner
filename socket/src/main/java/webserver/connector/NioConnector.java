package webserver.connector;

import webserver.processor.ServletProcessor;
import webserver.processor.StaticProcess;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * @Author yangxw
 * @Date 2020-11-02 上午9:22
 * @Description
 * @Version 1.0
 */
public class NioConnector {
    private static final int DEFAULT_PORT = 8888;
    private static final String QUIT = "quit";
    private static final int BUFFER = 1024;

    private ServerSocketChannel server;
    private Selector selector;
    private ByteBuffer rBuffer = ByteBuffer.allocate(BUFFER);
    private ByteBuffer wBuffer = ByteBuffer.allocate(BUFFER);
    private Charset charset = Charset.forName("UTF-8");
    private int port;

    public NioConnector() {
        this(DEFAULT_PORT);
    }

    public NioConnector(int port) {
        this.port = port;
    }

    private void start() {
        try {
            server = ServerSocketChannel.open();
            // 处于非阻塞式模型
            server.configureBlocking(false);
            server.socket().bind(new InetSocketAddress(port));

            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("启动服务器，监听端口：" + port + "...");

            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys =
                        selector.selectedKeys();
                for (SelectionKey key : selectionKeys) {
                    // 处理被触发的事件
                    handles(key);
                }
                selectionKeys.clear();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(selector);
        }
    }

    private void handles(SelectionKey key) throws IOException {
        // ACCEPT事件 - 和客户端建立了连接
        if (key.isAcceptable()) {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel client = serverSocketChannel.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
            System.out.println(getClientName(client) + "已连接");
        }
        // READ事件 - 客户端发送了消息
        else if (key.isReadable()) {
            SocketChannel client = (SocketChannel) key.channel();
            key.cancel();
            client.configureBlocking(false);
            Socket socket = client.socket();
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

    }

    /**
     * 转发数据
     *
     * @param client
     * @param forwardMessage
     * @throws IOException
     */
    private void forwardMessage(SocketChannel client, String forwardMessage) throws IOException {
        for (SelectionKey key : selector.keys()) {
            Channel connectedClient = key.channel();
            if (connectedClient instanceof ServerSocketChannel) {
                continue;
            }
            if (key.isValid() && !client.equals(connectedClient)) {
                wBuffer.clear();
                wBuffer.put(charset.encode(getClientName(client) + ":" + forwardMessage));
                wBuffer.flip();
                while (wBuffer.hasRemaining()) {
                    ((SocketChannel) connectedClient).write(wBuffer);
                }
            }
        }


    }

    private String receive(SocketChannel client) throws IOException {
        rBuffer.clear();
        while (client.read(rBuffer) > 0) ;
        rBuffer.flip();
        return String.valueOf(charset.decode(rBuffer));
    }


    private String getClientName(SocketChannel socketChannel) {
        return "客户端【" + socketChannel.socket().getPort() + "】";
    }

    public boolean readyQuit(String msg) {
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
}
