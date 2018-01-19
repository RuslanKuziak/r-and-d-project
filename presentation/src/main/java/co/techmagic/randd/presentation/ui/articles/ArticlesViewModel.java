package co.techmagic.randd.presentation.ui.articles;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.repository.impl.NewsRepositoryImpl;
import co.techmagic.randd.data.request.ArticleBookmarkRequest;
import co.techmagic.randd.data.request.EverythingInRangeRequest;
import co.techmagic.randd.domain.interactor.news.ArticleBookmarkInteractor;
import co.techmagic.randd.domain.interactor.news.EverythingInRangeInteractor;
import co.techmagic.randd.domain.interactor.news.GetCachedArticlesInteractor;
import co.techmagic.randd.domain.interactor.news.TopHeadlinesInteractor;
import co.techmagic.randd.presentation.BaseDisposableObserver;
import co.techmagic.randd.presentation.ui.base.BaseViewModel;

/**
 * Created by ruslankuziak on 12/14/17.
 */

public class ArticlesViewModel extends BaseViewModel {

    private TopHeadlinesInteractor newsInteractor;
    private EverythingInRangeInteractor inRangeInteractor;
    private GetCachedArticlesInteractor getArticlesDbInteractor;
    private ArticleBookmarkInteractor articleBookmarkInteractor;

    MutableLiveData<List<ArticleApp>> articlesData = new MutableLiveData<>();
    MutableLiveData<ArticleApp> articleData = new MutableLiveData<>();

    public ArticlesViewModel() {
        final NewsRepositoryImpl repository = new NewsRepositoryImpl();
        newsInteractor = new TopHeadlinesInteractor(repository);
        inRangeInteractor = new EverythingInRangeInteractor(repository);
        getArticlesDbInteractor = new GetCachedArticlesInteractor(repository);
        articleBookmarkInteractor = new ArticleBookmarkInteractor(repository);
        getArticlesInRange();
    }

    public void getArticlesInRange() {
        showProgress();
        EverythingInRangeRequest request = new EverythingInRangeRequest("google", "2017-12-01", "2018-01-17");
        inRangeInteractor.execute(request, new BaseDisposableObserver<List<ArticleApp>>(networkErrorLiveData) {
            @Override
            public void onNext(List<ArticleApp> articleApps) {
                super.onNext(articleApps);
                hideProgress();
                articlesData.postValue(articleApps);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                hideProgress();
            }
        });
    }

    public void bookmarkArticle(final ArticleApp articleApp) {
        ArticleBookmarkRequest request = new ArticleBookmarkRequest(articleApp);
        articleBookmarkInteractor.execute(request, new BaseDisposableObserver<Void>() {
            @Override
            public void onComplete() {
                super.onComplete();
                articleData.postValue(articleApp);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                articleData.postValue(null);
            }
        });
    }

    @Override
    protected void onCleared() {
        newsInteractor.dispose();
        inRangeInteractor.dispose();
        getArticlesDbInteractor.dispose();
        super.onCleared();
    }
}