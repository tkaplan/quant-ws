package quant.http.requests.builders;

/**
 * Created by dev on 1/1/15.
 */
public class StreamRequestBuilder {
    /**
     * S = Service Name (QUOTE,LEVELII,OPTION etc..)
     * C = GET(snapshot) or SUBS (streaming) or ADD o UNSUBS or VIEW
     *  - SUBS = Original subscription request
     *  - ADD = Adds to original subscription request
     *  - UNSUBS = Remove quotes for some or all symbols
     *  - VIEW = Change the fields subscribed for
     *  - SUBS = Changes the entire subscription
     *
     * P = Parameters (optional)
     * T = Field #'s
     */
    String S,C,P,T;

    public StreamRequestBuilder() {

    }

    public String build() {
        return "S=" + S + "&C=" + C + "&P=" + P + "&T=" + T;
    }

    public void SubscribeOnlyTo() {
        C = "SUBS";
    }

    public void Unsubsribe() {
        C = "UNSUBS";
    }

    public void Add() {
        C = "ADD";
    }

    public void setParameters(String p) {
        P = p;
    }

    public void setFields(String t) {
        T = t;
    }
}
