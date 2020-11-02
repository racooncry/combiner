package webserver.connector;

import javax.servlet.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * @Author yangxw
 * @Date 2020-10-31 下午5:15
 * @Description
 * @Version 1.0
 */
public class RequestFacade implements ServletRequest {
    private ServletRequest request = null;

    public RequestFacade(Request request) {
        this.request = request;
    }

    @Override
    public Object getAttribute(String s) {
        return this.request.getAttribute(s);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return  this.request.getAttributeNames();
    }

    @Override
    public String getCharacterEncoding() {
        return this.request.getCharacterEncoding();
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {
        this.request.setCharacterEncoding(s);
    }

    @Override
    public int getContentLength() {
        return this.request.getContentLength();
    }

    @Override
    public long getContentLengthLong() {
        return this.request.getContentLengthLong();
    }

    @Override
    public String getContentType() {
        return this.request.getContentType();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return this.request.getInputStream();
    }

    @Override
    public String getParameter(String s) {
        return this.request.getParameter(s);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return this.request.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String s) {
        return this.request.getParameterValues(s);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.request.getParameterMap();
    }

    @Override
    public String getProtocol() {
        return  this.request.getProtocol();
    }

    @Override
    public String getScheme() {
        return  this.request.getScheme();
    }

    @Override
    public String getServerName() {
        return  this.request.getServerName();
    }

    @Override
    public int getServerPort() {
        return  this.request.getServerPort();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return  this.request.getReader();
    }

    @Override
    public String getRemoteAddr() {
        return  this.request.getRemoteAddr();
    }

    @Override
    public String getRemoteHost() {
        return  this.request.getRemoteHost();
    }

    @Override
    public void setAttribute(String s, Object o) {
        this.request.setAttribute(s, o);
    }

    @Override
    public void removeAttribute(String s) {
        this.request.removeAttribute(s);
    }

    @Override
    public Locale getLocale() {
        return  this.request.getLocale();
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return  this.request.getLocales();
    }

    @Override
    public boolean isSecure() {
        return  this.request.isSecure();
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return  this.request.getRequestDispatcher(s);
    }

    @Override
    public String getRealPath(String s) {
        return  this.request.getRealPath(s);
    }

    @Override
    public int getRemotePort() {
        return  this.request.getRemotePort();
    }

    @Override
    public String getLocalName() {
        return  this.request.getLocalName();
    }

    @Override
    public String getLocalAddr() {
        return  this.request.getLocalAddr();
    }

    @Override
    public int getLocalPort() {
        return  this.request.getLocalPort();
    }

    @Override
    public ServletContext getServletContext() {
        return  this.request.getServletContext();
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return  this.request.startAsync();
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return  this.request.startAsync();
    }

    @Override
    public boolean isAsyncStarted() {
        return  this.request.isAsyncStarted();
    }

    @Override
    public boolean isAsyncSupported() {
        return  this.request.isAsyncSupported();
    }

    @Override
    public AsyncContext getAsyncContext() {
        return  this.request.getAsyncContext();
    }

    @Override
    public DispatcherType getDispatcherType() {
        return  this.request.getDispatcherType();
    }
}
