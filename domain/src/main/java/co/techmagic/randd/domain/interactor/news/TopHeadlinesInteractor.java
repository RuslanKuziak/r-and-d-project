package co.techmagic.randd.domain.interactor.news;

import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.network.exception.NoNetworkException;
import co.techmagic.randd.data.network.request.GetTopHeadlinesRequest;
import co.techmagic.randd.data.repository.impl.NewsRepositoryImpl;
import co.techmagic.randd.domain.interactor.BaseRequestInteractor;
import io.reactivex.Observable;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public class TopHeadlinesInteractor extends BaseRequestInteractor<GetTopHeadlinesRequest, List<ArticleApp>, NewsRepositoryImpl> {

    public TopHeadlinesInteractor(NewsRepositoryImpl newsApiManager) {
        super(newsApiManager);
    }

    @Override
    protected Observable<List<ArticleApp>> buildObservable(GetTopHeadlinesRequest requestData) {
        if (networkManager.isNetworkAvailable()) {
            return repository.getTopHeadlines(requestData.getSource());
        }

        return Observable.error(new NoNetworkException());
    }
}