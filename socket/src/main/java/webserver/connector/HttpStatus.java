package webserver.connector;

/**
 * @Author yangxw
 * @Date 2020-10-31 下午1:54
 * @Description
 * @Version 1.0
 */
public enum HttpStatus {
    SC_OK(200,"OK"),
    SC_NOT_FOUND(404,"File Not Found");

    private int statusCode;
    private String reason;

    HttpStatus(int statusCode, String reason) {
        this.statusCode = statusCode;
        this.reason = reason;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getReason() {
        return reason;
    }
}
