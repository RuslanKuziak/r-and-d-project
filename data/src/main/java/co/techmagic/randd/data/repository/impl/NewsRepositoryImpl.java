package co.techmagic.randd.data.repository.impl;

import android.util.Log;

import java.sql.SQLException;
import java.util.List;

import co.techmagic.randd.data.Mapper;
import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.db.DataException;
import co.techmagic.randd.data.db.entity.ArticleEntity;
import co.techmagic.randd.data.db.manager.DbManager;
import co.techmagic.randd.data.db.manager.DbManagerImpl;
import co.techmagic.randd.data.network.client.ApiClient;
import co.techmagic.randd.data.network.entity.ArticleResponse;
import co.techmagic.randd.data.network.service.NewsService;
import co.techmagic.randd.data.repository.BaseRepository;
import co.techmagic.randd.data.repository.NewsRepository;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public class NewsRepositoryImpl extends BaseRepository implements NewsRepository {

    private static final String API_KEY = "bb64160866274f01a1588d25512fba09";
    private NewsService service;
    private DbManager dbManager;
    private Mapper mapper;

    public NewsRepositoryImpl() {
        service = ApiClient.getNewsService();
        dbManager = new DbManagerImpl();
        mapper = new Mapper();
    }

    @Override
    public Observable<List<ArticleApp>> getTopHeadlines(String sources) {
        return service.getTopHeadlines(sources, API_KEY)
                .map(new Function<ArticleResponse, List<ArticleApp>>() {
                    @Override
                    public List<ArticleApp> apply(ArticleResponse articleResponse) {
                        List articles = mapper.mapArticleResponseEntities(articleResponse);
                        return articles;
                    }
                });
    }

    /**
     * Getting articles from API and saving them into DB
     * */

    @Override
    public Observable<List<ArticleApp>> getEverythingInRange(String query, String from, String to) {
        return service.getEverythingInRange(query, from, to, API_KEY)
                .map(new Function<ArticleResponse, List<ArticleApp>>() {
                    @Override
                    public List<ArticleApp> apply(ArticleResponse articleResponse) {
                        return mapper.mapArticleResponseEntities(articleResponse);
                    }
                }).flatMap(new Function<List<ArticleApp>, Observable<List<ArticleApp>>>() {
                    @Override
                    public Observable<List<ArticleApp>> apply(List<ArticleApp> articleApps) {
                        return saveEverythingInRangeToDb(articleApps);
                    }
                });
    }

    @Override
    public Observable<List<ArticleApp>> getCachedArticles() {
        return Observable.create(new ObservableOnSubscribe<List<ArticleApp>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ArticleApp>> emitter) {
                try {
                    List<ArticleEntity> entities = dbManager.getArticles();

                    emitter.onNext(mapper.mapArticleDbEntities(entities));
                    emitter.onComplete();
                } catch (SQLException exception) {
                    emitter.onError(new DataException());
                }
            }
        });
    }

    @Override
    public Observable<List<ArticleApp>> saveEverythingInRangeToDb(final List<ArticleApp> articles) {
        return Observable.create(new ObservableOnSubscribe<List<ArticleApp>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ArticleApp>> emitter) {
                List<ArticleEntity> entities = mapper.mapAppEntities(articles);
                try {
                    dbManager.insertArticles(entities);
                    emitter.onNext(articles);
                    emitter.onComplete();
                } catch (SQLException exception) {
                    emitter.onError(new DataException());
                    Log.e("NewsRepositoryImpl", "Error occurred while caching articles");
                }
            }
        });
    }

    @Override
    public Observable<Void> deleteAllArticles() {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> emitter) {
                try {
                    dbManager.deleteAllFromTable();
                    emitter.onComplete();
                } catch (SQLException exception) {
                    emitter.onError(new DataException());
                }
            }
        });
    }

    @Override
    public Observable<Void> bookmarkArticle(final ArticleApp articleApp) {
        return Observable.create(new ObservableOnSubscribe<Void>() {
            @Override
            public void subscribe(ObservableEmitter<Void> emitter) {
                try {
                    ArticleEntity entity = mapper.mapAppToDbEntity(articleApp);
                    dbManager.bookmarkArticle(entity);
                    emitter.onComplete();
                } catch (SQLException e) {
                    emitter.onError(new DataException());
                    e.printStackTrace();
                }
            }
        });
    }
}