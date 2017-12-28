package co.techmagic.randd.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.application.ArticleInfoApp;
import co.techmagic.randd.data.application.SourceApp;
import co.techmagic.randd.data.db.entity.ArticleEntity;
import co.techmagic.randd.data.network.entity.ArticleInfo;
import co.techmagic.randd.data.network.entity.ArticleResponse;

/**
 * Created by ruslankuziak on 12/28/17.
 */

public class Mapper {

    @NonNull
    public List<ArticleApp> mapArticles(@Nullable ArticleResponse articleResponse) {
        List<ArticleApp> articles = new ArrayList<>();
        if (articleResponse == null) {
            return articles;
        }

        List<ArticleInfo> articleInfos = articleResponse.getArticleInfos();
        if (articleInfos != null) {
            for (ArticleInfo info : articleInfos) {
                ArticleApp articleApp = new ArticleApp();
                articleApp.setSourceApp(mapSource(info)); // TODO remove
                articleApp.setArticleInfoApp(mapArticleInfo(info));
                articles.add(articleApp);
            }
        }
        return articles;
    }

    @NonNull
    public List<ArticleApp> mapArticles(@Nullable List<ArticleEntity> entities) {
        List<ArticleApp> articles = new ArrayList<>();
        if (entities == null || entities.isEmpty()) {
            return articles;
        }

        for (ArticleEntity entity : entities) {
            ArticleApp articleApp = new ArticleApp();
            articleApp.setArticleInfoApp(mapArticleInfo(entity));
            articles.add(articleApp);
        }

        return articles;
    }

    @Nullable
    private SourceApp mapSource(@Nullable ArticleInfo info) {
        if (info == null || info.getSource() == null) {
            return null;
        }

        SourceApp sourceApp = new SourceApp();
        sourceApp.setId(info.getSource().getId());
        sourceApp.setName(info.getSource().getName());
        return sourceApp;
    }

    @Nullable
    private SourceApp mapSource(@Nullable ArticleEntity entity) {
        if (entity == null || entity.getSourceApp() == null) {
            return null;
        }

        SourceApp sourceApp = new SourceApp();
        sourceApp.setId(entity.getSourceApp().getId());
        sourceApp.setName(entity.getSourceApp().getName());
        return sourceApp;
    }

    @Nullable
    private ArticleInfoApp mapArticleInfo(@Nullable ArticleInfo info) {
        if (info == null) {
            return null;
        }

        ArticleInfoApp articleInfoApp = new ArticleInfoApp();
        articleInfoApp.setAuthor(info.getAuthor());
        articleInfoApp.setDate(info.getDate());
        articleInfoApp.setDescription(info.getDescription());
        articleInfoApp.setTitle(info.getTitle());
        articleInfoApp.setUrl(info.getUrl());
        articleInfoApp.setUrlToImage(info.getUrlToImage());
        articleInfoApp.setSource(mapSource(info));
        return articleInfoApp;
    }

    @Nullable
    private ArticleInfoApp mapArticleInfo(@Nullable ArticleEntity entity) {
        if (entity == null) {
            return null;
        }

        ArticleInfoApp articleInfoApp = new ArticleInfoApp();
        articleInfoApp.setAuthor(entity.getAuthor());
        articleInfoApp.setDate(entity.getDate());
        articleInfoApp.setDescription(entity.getDescription());
        articleInfoApp.setTitle(entity.getTitle());
        articleInfoApp.setUrl(entity.getUrl());
        articleInfoApp.setUrlToImage(entity.getUrlToImage());
        articleInfoApp.setSource(mapSource(entity));
        return articleInfoApp;
    }

    @Nullable
    public List<ArticleEntity> mapEntities(List<ArticleApp> articles) {
        if (articles == null || articles.isEmpty()) {
            return null;
        }

        List<ArticleEntity> entities = new ArrayList<>();

        for (ArticleApp article : articles) {
            ArticleEntity entity = new ArticleEntity();
            entity.setAuthor(article.getArticleInfoApp().getAuthor());
            entity.setDate(article.getArticleInfoApp().getDate());
            entity.setDescription(article.getArticleInfoApp().getDescription());
            entity.setTitle(article.getArticleInfoApp().getTitle());
            entity.setUrl(article.getArticleInfoApp().getUrl());
            entity.setUrlToImage(article.getArticleInfoApp().getUrlToImage());

            entities.add(entity);
        }

        return entities;
    }
}