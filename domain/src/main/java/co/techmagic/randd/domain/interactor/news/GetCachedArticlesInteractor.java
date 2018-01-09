package co.techmagic.randd.domain.interactor.news;

import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.repository.impl.NewsRepositoryImpl;
import co.techmagic.randd.domain.interactor.BaseInteractor;
import io.reactivex.Observable;

/**
 * Created by ruslankuziak on 12/28/17.
 */

public class GetCachedArticlesInteractor extends BaseInteractor<List<ArticleApp>, NewsRepositoryImpl> {

    public GetCachedArticlesInteractor(NewsRepositoryImpl repository) {
        super(repository);
    }

    @Override
    protected Observable<List<ArticleApp>> buildObservable() {
        return repository.getCachedArticles();
    }
}