package co.techmagic.randd.data.network.manager;

import java.util.ArrayList;
import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.application.ArticleInfoApp;
import co.techmagic.randd.data.application.SourceApp;
import co.techmagic.randd.data.network.NewsApi;
import co.techmagic.randd.data.network.client.ApiClient;
import co.techmagic.randd.data.network.entity.ArticleInfo;
import co.techmagic.randd.data.network.entity.ArticleResponse;
import co.techmagic.randd.data.network.repository.NewsRepository;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ruslankuziak on 12/19/17.
 */

public class NewsApiManager extends BaseManager implements NewsApi {

    private NewsRepository repository;

    public NewsApiManager() {
        repository = ApiClient.getNewsRepository();
    }

    @Override
    public Observable<List<ArticleApp>> getTopHeadlines(String sources, String apiKey) {
        return repository.getTopHeadlines(sources, apiKey)
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