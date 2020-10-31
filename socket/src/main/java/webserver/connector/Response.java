package webserver.connector;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

/**
 * @Author yangxw
 * @Date 2020-10-30 上午9:38
 * @Description
 * @Version 1.0
 */
public class Response implements ServletResponse {
    private static final int BUFFER_SIZE = 2048;
    Request request;
    OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        File file = new File(ConnectorUtils.WEB_ROOT + request.getUri());
        try {
            System.out.println("file path:" + file.getAbsolutePath());
            write(file, HttpStatus.SC_OK);

        } catch (IOException e) {
            e.printStackTrace();

            write(new File(ConnectorUtils.WEB_ROOT + "\\404.html"), HttpStatus.SC_NOT_FOUND);
        }

    }

    private void write(File resource, HttpStatus status) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(resource);
            outputStream.write(ConnectorUtils.renderStataus(status).getBytes());
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = fis.read(buffer, 0, BUFFER_SIZE)) != -1) {
                System.out.println("length:" + length);
                outputStream.write(buffer, 0
                        , length);
            }

        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        PrintWriter writer = new PrintWriter(outputStream, true);
        return writer;
    }

    @Override
    public void setCharacterEncoding(String s) {

    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentLengthLong(long l) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }
}
