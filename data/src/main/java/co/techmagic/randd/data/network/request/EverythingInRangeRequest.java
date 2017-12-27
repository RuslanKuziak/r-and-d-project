package co.techmagic.randd.data.network.request;

/**
 * Created by ruslankuziak on 12/27/17.
 */

public class EverythingInRangeRequest extends BaseRequest {

    private String query;
    private String from;
    private String to;

    public EverythingInRangeRequest(String query, String from, String to) {
        this.query = query;
        this.from = from;
        this.to = to;
    }

    public String getQuery() {
        return query;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}