package co.techmagic.randd.data.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ruslankuziak on 12/15/17.
 */

public class ResponseBody {

    @SerializedName("totalResults")
    private int totalResults;

    @SerializedName("articles")
    private List<Article> articles;

    public int getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }
}