package quant.stream.manager;

/**
 * Created by dev on 1/6/15.
 */
public enum Status {

    OK(0, "Request Success!"),
    ERROR(1, "Request Was Not Successful."),
    TIMEOUT(2, "Request Timeout.");

    private final int code;
    private final String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
