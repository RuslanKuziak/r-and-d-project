package co.techmagic.randd.data.repository;

import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import io.reactivex.Observable;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public interface NewsRepository {

    Observable<List<ArticleApp>> getTopHeadlines(String sources);

    Observable<List<ArticleApp>> getEverythingInRange(String query, String from, String to);
    
    Observable<List<ArticleApp>> getCachedArticles();

    Observable<List<ArticleApp>> saveEverythingInRangeToDb(List<ArticleApp> articles);

    Observable<Void> deleteAllArticles();

    Observable<Void> bookmarkArticle(ArticleApp articleApp);
}