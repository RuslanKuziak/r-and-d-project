package co.techmagic.aand.data.network.service;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by ruslankuziak on 12/15/17.
 */

public interface NewsService {

    @GET("/v2/top-headlines")
    Observable<ResponseBody> getTopHeadlines();
}