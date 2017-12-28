package co.techmagic.randd.domain.interactor.news;

import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.repository.impl.NewsRepositoryImpl;
import co.techmagic.randd.domain.interactor.BaseDataInteractor;
import io.reactivex.Observable;

/**
 * Created by ruslankuziak on 12/28/17.
 */

public class InsertArticlesDbInteractor extends BaseDataInteractor<List<ArticleApp>, Void, NewsRepositoryImpl> {

    public InsertArticlesDbInteractor(NewsRepositoryImpl repository) {
        super(repository);
    }

    @Override
    protected Observable<Void> buildObservable(List<ArticleApp> data) {
        return repository.saveEverythingInRangeToDb(data);
    }
}