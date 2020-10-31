package webserver.test;

import java.io.*;
import java.net.Socket;

/**
 * @Author yangxw
 * @Date 2020-10-31 下午3:35
 * @Description
 * @Version 1.0
 */
public class TestClient {
    private static final int BUFFER_SIZE = 2048;

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 8888);
        OutputStream outputStream = socket.getOutputStream();
//        outputStream.write("GET /index.html HTTP/1.1".getBytes());
        outputStream.write("GET /servlet/TimeServlet HTTP/1.1".getBytes());


        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();

//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        String line = "";
//        StringBuffer stringBuffer = new StringBuffer();
//        while ((line = reader.readLine()) != null) {
//            stringBuffer.append(line);
//        }
//        System.out.println(stringBuffer.toString());
//


        StringBuilder stringBuilder = new StringBuilder();
        int length;
        while ((length = inputStream.read()) != -1) {
            stringBuilder.append((char)length);
        }
        System.out.println(stringBuilder.toString());


        socket.shutdownInput();

        socket.close();
    }
}
