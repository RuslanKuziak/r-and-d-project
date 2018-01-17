package co.techmagic.randd.domain.interactor.news;

import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.network.exception.NoNetworkException;
import co.techmagic.randd.data.request.EverythingInRangeRequest;
import co.techmagic.randd.data.repository.impl.NewsRepositoryImpl;
import co.techmagic.randd.domain.interactor.BaseDataInteractor;
import io.reactivex.Observable;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public class EverythingInRangeInteractor extends BaseDataInteractor<EverythingInRangeRequest, List<ArticleApp>, NewsRepositoryImpl> {

    public EverythingInRangeInteractor(NewsRepositoryImpl newsApiManager) {
        super(newsApiManager);
    }

    @Override
    protected Observable<List<ArticleApp>> buildObservable(EverythingInRangeRequest requestData) {
        if (networkManager.isNetworkAvailable()) {
            return repository.getEverythingInRange(requestData.getQuery(), requestData.getFrom(), requestData.getTo());
        }

        return Observable.error(new NoNetworkException());
    }
}