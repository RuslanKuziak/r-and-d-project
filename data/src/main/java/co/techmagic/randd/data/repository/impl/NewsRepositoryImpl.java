package co.techmagic.randd.data.repository.impl;

import java.util.ArrayList;
import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.application.ArticleInfoApp;
import co.techmagic.randd.data.application.SourceApp;
import co.techmagic.randd.data.network.client.ApiClient;
import co.techmagic.randd.data.network.entity.ArticleInfo;
import co.techmagic.randd.data.network.entity.ArticleResponse;
import co.techmagic.randd.data.network.service.NewsService;
import co.techmagic.randd.data.repository.BaseRepository;
import co.techmagic.randd.data.repository.NewsRepository;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public class NewsRepositoryImpl extends BaseRepository implements NewsRepository {

    private static final String API_KEY = "bb64160866274f01a1588d25512fba09";
    private NewsService repository;

    public NewsRepositoryImpl() {
        repository = ApiClient.getNewsRepository();
    }

    @Override
    public Observable<List<ArticleApp>> getTopHeadlines(String sources) {
        return repository.getTopHeadlines(sources, API_KEY)
                .map(new Function<ArticleResponse, List<ArticleApp>>() {
                    @Override
                    public List<ArticleApp> apply(ArticleResponse articleResponse) throws Exception {
                        List articles = mapArticles(articleResponse);
                        return articles;
                    }
                });
    }

    @Override
    public Observable<List<ArticleApp>> getEverythingInRange(String query, String from, String to) {
        return repository.getEverythingInRange(query, from, to, API_KEY)
                .map(new Function<ArticleResponse, List<ArticleApp>>() {
                    @Override
                    public List<ArticleApp> apply(ArticleResponse articleResponse) throws Exception {
                        List articles = mapArticles(articleResponse);
                        return articles;
                    }
                });
    }

    private List<ArticleApp> mapArticles(ArticleResponse articleResponse) {
        List<ArticleApp> articles = new ArrayList<>();
        List<ArticleInfo> articleInfos = articleResponse.getArticleInfos();
        if (articleInfos != null) {
            for (ArticleInfo info : articleInfos) {
                ArticleApp articleApp = new ArticleApp();
                articleApp.setSourceApp(mapSource(info));
                articleApp.setArticleInfoApp(mapArticleInfo(info));
                articles.add(articleApp);
            }
        }
        return articles;
    }

    private SourceApp mapSource(ArticleInfo info) {
        SourceApp sourceApp = new SourceApp();
        sourceApp.setId(info.getSource().getId());
        sourceApp.setName(info.getSource().getName());
        return sourceApp;
    }

    private ArticleInfoApp mapArticleInfo(ArticleInfo info) {
        ArticleInfoApp articleInfoApp = new ArticleInfoApp();
        articleInfoApp.setAuthor(info.getAuthor());
        articleInfoApp.setDate(info.getDate());
        articleInfoApp.setDescription(info.getDescription());
        articleInfoApp.setTitle(info.getTitle());
        articleInfoApp.setUrl(info.getUrl());
        articleInfoApp.setUrlToImage(info.getUrlToImage());
        return articleInfoApp;
    }
}