package chat.aio.client;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author yangxw
 * @Date 2020-10-27 上午9:45
 * @Description
 * @Version 1.0
 */
public class Client {
    private static final int DEFAULT_PORT = 8888;
    private static final String LOCAL_HOST = "localhost";
    AsynchronousSocketChannel clientChannel;

    private void start() {
        // 创建 channel
        try {
            clientChannel = AsynchronousSocketChannel.open();
            Future<Void> future = clientChannel.connect(new InetSocketAddress(LOCAL_HOST, DEFAULT_PORT));
            // 阻塞式
            future.get();

            // 等待用户的输入
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String input = consoleReader.readLine();
                byte[] inputBytes = input.getBytes();
                ByteBuffer buffer = ByteBuffer.wrap(inputBytes);
                Future<Integer> write = clientChannel.write(buffer);

            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
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
