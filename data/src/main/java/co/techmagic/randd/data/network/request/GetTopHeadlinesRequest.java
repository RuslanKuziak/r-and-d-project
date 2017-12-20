package co.techmagic.randd.data.network.request;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public class GetTopHeadlinesRequest extends BaseRequest {

    private String source;
    private String apiKey;

    public GetTopHeadlinesRequest(String source, String apiKey) {
        this.source = source;
        this.apiKey = apiKey;
    }

    public String getSource() {
        return source;
    }

    public String getApiKey() {
        return apiKey;
    }
}