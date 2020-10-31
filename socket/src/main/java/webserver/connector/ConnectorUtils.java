package webserver.connector;

/**
 * @Author yangxw
 * @Date 2020-10-31 下午1:58
 * @Description
 * @Version 1.0
 */
public class ConnectorUtils {

    public static final String PROTOCOL = "HTTP/1.1";
    public static final String CARRIAGE = "\r";
    public static final String NEWLINE = "\n";
    public static final String SPACE = " ";

    public static final String WEB_ROOT  = "D:\\yangxw\\work\\my_project\\yxw\\combiner\\socket\\src\\webroot";

    public static String renderStataus(HttpStatus status) {
        StringBuilder append = new StringBuilder(PROTOCOL)
                .append(SPACE)
                .append(status.getStatusCode())
                .append(SPACE)
                .append(status.getReason())
                .append(CARRIAGE).append(NEWLINE)
                .append(CARRIAGE).append(NEWLINE);
        return append.toString();

    }
}
