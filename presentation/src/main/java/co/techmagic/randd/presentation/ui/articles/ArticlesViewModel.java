package co.techmagic.randd.presentation.ui.articles;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.network.request.EverythingInRangeRequest;
import co.techmagic.randd.data.network.request.GetTopHeadlinesRequest;
import co.techmagic.randd.data.repository.impl.NewsRepositoryImpl;
import co.techmagic.randd.domain.interactor.news.EverythingInRangeInteractor;
import co.techmagic.randd.domain.interactor.news.GetArticlesDbInteractor;
import co.techmagic.randd.domain.interactor.news.TopHeadlinesInteractor;
import co.techmagic.randd.presentation.BaseDisposableObserver;
import co.techmagic.randd.presentation.ui.base.BaseViewModel;

/**
 * Created by ruslankuziak on 12/14/17.
 */

public class ArticlesViewModel extends BaseViewModel {

    private TopHeadlinesInteractor newsInteractor;
    private EverythingInRangeInteractor inRangeInteractor;
    private GetArticlesDbInteractor getArticlesDbInteractor;

    MutableLiveData<List<ArticleApp>> articles = new MutableLiveData<>();

    public ArticlesViewModel() {
        final NewsRepositoryImpl repository = new NewsRepositoryImpl();
        newsInteractor = new TopHeadlinesInteractor(repository);
        inRangeInteractor = new EverythingInRangeInteractor(repository);
        getArticlesDbInteractor = new GetArticlesDbInteractor(repository);
        getEverythingInRange();
    }

    public void getTopHeadlines() {
        showProgress();
        GetTopHeadlinesRequest request = new GetTopHeadlinesRequest("google-news");
        newsInteractor.execute(request, new BaseDisposableObserver<List<ArticleApp>>(networkErrorLiveData) {
            @Override
            public void onNext(List<ArticleApp> articleApps) {
                super.onNext(articleApps);
                hideProgress();
                articles.postValue(articleApps);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                hideProgress();
            }
        });
    }

    public void getEverythingInRange() {
        showProgress();
        EverythingInRangeRequest request = new EverythingInRangeRequest("google", "2017-12-01", "2017-12-27");
        inRangeInteractor.execute(request, new BaseDisposableObserver<List<ArticleApp>>(networkErrorLiveData) {
            @Override
            public void onNext(List<ArticleApp> articleApps) {
                super.onNext(articleApps);
                hideProgress();
                articles.postValue(articleApps);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                hideProgress();
            }
        });
    }

    public void getCachedArticles() {
        getArticlesDbInteractor.execute(new BaseDisposableObserver<List<ArticleApp>>() {
            @Override
            public void onNext(List<ArticleApp> articleApps) {
                super.onNext(articleApps);
                articles.postValue(articleApps);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
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