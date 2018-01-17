package co.techmagic.randd.domain.interactor.news;

import co.techmagic.randd.data.repository.impl.NewsRepositoryImpl;
import co.techmagic.randd.data.request.ArticleBookmarkRequest;
import co.techmagic.randd.domain.interactor.BaseDataInteractor;
import io.reactivex.Observable;

/**
 * Created by ruslankuziak on 1/17/18.
 */

public class ArticleBookmarkInteractor extends BaseDataInteractor<ArticleBookmarkRequest, Void, NewsRepositoryImpl> {

    public ArticleBookmarkInteractor(NewsRepositoryImpl repository) {
        super(repository);
    }

    @Override
    protected Observable<Void> buildObservable(ArticleBookmarkRequest requestData) {
        return repository.bookmarkArticle(requestData.getArticleApp());
    }
}