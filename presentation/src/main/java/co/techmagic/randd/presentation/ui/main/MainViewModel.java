package co.techmagic.randd.presentation.ui.main;

import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.network.manager.NewsApiManager;
import co.techmagic.randd.data.network.request.GetTopHeadlinesRequest;
import co.techmagic.randd.domain.interactor.news.NewsRequestInteractor;
import co.techmagic.randd.presentation.BaseDisposableObserver;
import co.techmagic.randd.presentation.ui.base.BaseViewModel;

/**
 * Created by ruslankuziak on 12/14/17.
 */

public class MainViewModel extends BaseViewModel {

    private NewsRequestInteractor newsInteractor;
    MutableLiveData<List<ArticleApp>> articles = new MutableLiveData<>();

    public MainViewModel() {
        newsInteractor = new NewsRequestInteractor(new NewsApiManager());
        getTopHeadlines();
    }

    public void getTopHeadlines() {
        showProgress();
        GetTopHeadlinesRequest request = new GetTopHeadlinesRequest("google-news", "bb64160866274f01a1588d25512fba09");
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

    @Override
    protected void onCleared() {
        newsInteractor.unsubscribe();
        super.onCleared();
    }
}