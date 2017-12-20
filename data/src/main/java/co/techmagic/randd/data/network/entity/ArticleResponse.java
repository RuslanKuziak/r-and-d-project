package co.techmagic.randd.data.network.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ruslankuziak on 12/15/17.
 */

public class ArticleResponse {

    @SerializedName("totalResults")
    private int totalResults;

    @SerializedName("articles")
    private List<ArticleInfo> articleInfos;

    public int getTotalResults() {
        return totalResults;
    }

    public List<ArticleInfo> getArticleInfos() {
        return articleInfos;
    }
}