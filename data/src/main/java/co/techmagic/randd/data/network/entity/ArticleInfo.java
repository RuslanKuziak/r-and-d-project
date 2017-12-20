package co.techmagic.randd.data.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ruslankuziak on 12/15/17.
 */

public class ArticleInfo {

    @SerializedName("author")
    private String author;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("url")
    private String url;

    @SerializedName("urlToImage")
    private String urlToImage;

    @SerializedName("publishedAt")
    private String date;

    @SerializedName("source")
    private Source source;

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getDate() {
        return date;
    }

    public Source getSource() {
        return source;
    }
}