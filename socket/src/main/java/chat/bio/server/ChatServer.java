package chat.bio.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private int DEFAULT_PORT = 8888;
    private final String QUIT = "quit";

    private ExecutorService executorService;
    private ServerSocket serverSocket;
    private Map<Integer, Writer> connectionClients;

    public ChatServer() {
        executorService = Executors.newFixedThreadPool(2);
        connectionClients = new ConcurrentHashMap<>();
    }

    /**
     * 添加客户端
     *
     * @param socket
     * @throws IOException
     */
    public synchronized void addClients(Socket socket) throws IOException {
        if (socket != null) {
            int port = socket.getPort();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            connectionClients.put(port, writer);
            System.out.println("客户端【" + port + "】已经连接到服务器");
        }
    }

    /**
     * 移除
     *
     * @param socket
     * @throws IOException
     */
    public synchronized void removeClient(Socket socket) throws IOException {
        if (socket != null) {
            int port = socket.getPort();
            if (connectionClients.containsKey(port)) {
                connectionClients.get(port).close();
            }
            connectionClients.remove(port);
            System.out.println("客户端【" + port + "】已经下线");
        }
    }


    /**
     * 发送给其他人
     *
     * @param socket
     * @param message
     * @throws IOException
     */
    public synchronized void forwardMessage(Socket socket, String message) throws IOException {
        for (Integer id :
                connectionClients.keySet()) {
            // 不需要给自己发
            if (!id.equals(socket.getPort())) {
                Writer writer = connectionClients.get(id);
                writer.write(message);
                writer.flush();
            }

        }
    }


    public boolean readyQuit(String msg) {
        return QUIT.equals(msg);
    }

    /**
     * 关闭serverSocket
     */
    public synchronized void close() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void start() {
        // 绑定监听端口

        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("启动服务器，监听端口:" + DEFAULT_PORT);
            while (true) {
                // 等待客户端连接
                Socket socket = serverSocket.accept();
                // 创建一个ChatHandler线程
                // new Thread(new ChatHandler(this, socket)).start();

                // 伪异步，从线程池获得线程
                executorService.execute(new ChatHandler(this, socket));

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }
}
