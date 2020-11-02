package webserver.processor;


import webserver.connector.*;


import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @Author yangxw
 * @Date 2020-10-31 下午4:55
 * @Description
 * @Version 1.0
 */
public class ServletProcessor {


    public void process(Request request, Response response) throws MalformedURLException {
        URLClassLoader loader = this.getServletLoader();

        try {
            Servlet servlet = getServlet(loader, request);
            RequestFacade requestFacade = new RequestFacade(request);
            ResponseFacade responseFacade = new ResponseFacade(response);
            //  servlet.service(request, response);
            servlet.service(requestFacade, responseFacade);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public URLClassLoader getServletLoader() throws MalformedURLException {
        File file = new File("D:\\yangxw\\work\\my_project\\yxw\\combiner\\socket\\src\\webroot");
        URL url = file.toURI().toURL();
        System.out.println(url);
        return new URLClassLoader(new URL[]{url});
    }

    public Servlet getServlet(URLClassLoader loader, Request request) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        Class aClass = loader.loadClass(servletName);
        Servlet servlet = (Servlet) aClass.newInstance();
        return servlet;
    }


}
