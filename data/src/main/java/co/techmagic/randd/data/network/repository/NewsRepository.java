package co.techmagic.randd.data.network.repository;

import co.techmagic.randd.data.network.entity.ArticleResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ruslankuziak on 12/15/17.
 */

public interface NewsRepository {

    @GET("/v2/top-headlines")
    Observable<ArticleResponse> getTopHeadlines(@Query("sources") String sources, @Query("apiKey") String apiKey);
}