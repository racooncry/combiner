package webserver.connector;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * @Author yangxw
 * @Date 2020-10-31 下午5:15
 * @Description
 * @Version 1.0
 */
public class ResponseFacade implements ServletResponse {
    private Response response;

    public ResponseFacade(Response response) {
        this.response = response;
    }

    @Override
    public String getCharacterEncoding() {
        return this.response.getCharacterEncoding();
    }

    @Override
    public String getContentType() {
        return this.response.getContentType();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return this.response.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return this.response.getWriter();
    }

    @Override
    public void setCharacterEncoding(String s) {
        this.response.setCharacterEncoding(s);
    }

    @Override
    public void setContentLength(int i) {
        this.response.setContentLength(i);
    }

    @Override
    public void setContentLengthLong(long l) {
        this.response.setContentLengthLong(l);
    }

    @Override
    public void setContentType(String s) {
        this.response.setContentType(s);
    }

    @Override
    public void setBufferSize(int i) {
        this.response.setBufferSize(i);
    }

    @Override
    public int getBufferSize() {
        return this.response.getBufferSize();
    }

    @Override
    public void flushBuffer() throws IOException {
        this.response.flushBuffer();
    }

    @Override
    public void resetBuffer() {
        this.response.resetBuffer();
    }

    @Override
    public boolean isCommitted() {
        return this.response.isCommitted();
    }

    @Override
    public void reset() {
        this.response.reset();
    }

    @Override
    public void setLocale(Locale locale) {
        this.response.setLocale(locale);
    }

    @Override
    public Locale getLocale() {
        return this.response.getLocale();
    }
}
