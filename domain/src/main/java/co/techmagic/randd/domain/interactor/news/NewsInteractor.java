package co.techmagic.randd.domain.interactor.news;

import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.network.manager.NewsApiManager;
import co.techmagic.randd.data.network.request.GetTopHeadlinesRequest;
import co.techmagic.randd.domain.interactor.RequestBaseInteractor;
import rx.Observable;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public class NewsInteractor extends RequestBaseInteractor<GetTopHeadlinesRequest, List<ArticleApp>, NewsApiManager> {

    public NewsInteractor(NewsApiManager newsApiManager) {
        super(newsApiManager);
    }

    @Override
    protected Observable<List<ArticleApp>> buildObservable(GetTopHeadlinesRequest requestData) {
        return manager.getTopHeadlines(requestData.getSource(), requestData.getApiKey());
    }
}