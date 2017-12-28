package co.techmagic.randd.domain.interactor.news;

import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.repository.impl.NewsRepositoryImpl;
import co.techmagic.randd.domain.interactor.BaseInteractor;
import io.reactivex.Observable;

/**
 * Created by ruslankuziak on 12/28/17.
 */

public class GetArticlesDbInteractor extends BaseInteractor<List<ArticleApp>, NewsRepositoryImpl> {

    public GetArticlesDbInteractor(NewsRepositoryImpl repository) {
        super(repository);
    }

    @Override
    protected Observable<List<ArticleApp>> buildObservable() {
        return repository.getEverythingInRangeFromDb();
    }
}