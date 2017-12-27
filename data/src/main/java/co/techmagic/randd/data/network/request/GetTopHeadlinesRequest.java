package co.techmagic.randd.data.network.request;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public class GetTopHeadlinesRequest extends BaseRequest {

    private String source;

    public GetTopHeadlinesRequest(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}