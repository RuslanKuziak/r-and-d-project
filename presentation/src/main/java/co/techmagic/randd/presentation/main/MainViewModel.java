package co.techmagic.randd.presentation.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.network.manager.NewsApiManager;
import co.techmagic.randd.data.network.request.GetTopHeadlinesRequest;
import co.techmagic.randd.domain.interactor.news.NewsInteractor;
import co.techmagic.randd.domain.subscriber.BaseSubscriber;

/**
 * Created by ruslankuziak on 12/14/17.
 */

public class MainViewModel extends ViewModel {

    private NewsInteractor newsInteractor;
    MutableLiveData<List<ArticleApp>> articles = new MutableLiveData<>();

    public MainViewModel() {
        newsInteractor = new NewsInteractor(new NewsApiManager());
        getTopHeadlines();
    }

    private void getTopHeadlines() {
        GetTopHeadlinesRequest request = new GetTopHeadlinesRequest("google-news", "bb64160866274f01a1588d25512fba09");
        newsInteractor.execute(request, new BaseSubscriber<List<ArticleApp>>() {
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
}