package co.techmagic.randd.data.network.service;

import co.techmagic.randd.data.network.entity.ArticleResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ruslankuziak on 12/15/17.
 */

public interface NewsService {

    @GET("/v2/top-headlines")
    Observable<ArticleResponse> getTopHeadlines(@Query("sources") String sources, @Query("apiKey") String apiKey);

    @GET("v2/everything")
    Observable<ArticleResponse> getEverythingInRange(@Query("q") String query, @Query("from") String from, @Query("to") String to, @Query("apiKey") String apiKey);
}