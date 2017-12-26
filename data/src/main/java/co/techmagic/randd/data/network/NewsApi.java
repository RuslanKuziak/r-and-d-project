package co.techmagic.randd.data.network;

import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import io.reactivex.Observable;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public interface NewsApi {

    Observable<List<ArticleApp>> getTopHeadlines(String sources, String apiKey);
}