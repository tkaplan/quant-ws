package quant.stream.manager;

/**
 * Created by dev on 1/6/15.
 */
public class StatusHolder {
    private Status status;
    public StatusHolder() {

    }
    public void set(Status status) {
        this.status = status;
    }
    public Status get() {
        return status;
    }
}
