package co.techmagic.randd.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import co.techmagic.randd.data.application.ArticleApp;
import co.techmagic.randd.data.application.SourceApp;
import co.techmagic.randd.data.db.entity.ArticleEntity;
import co.techmagic.randd.data.network.entity.ArticleInfo;
import co.techmagic.randd.data.network.entity.ArticleResponse;

/**
 * Created by ruslankuziak on 12/28/17.
 */

public class Mapper {

    @NonNull
    public List<ArticleApp> mapArticleResponseEntities(@Nullable ArticleResponse articleResponse) {
        List<ArticleApp> articles = new ArrayList<>();
        if (articleResponse == null) {
            return articles;
        }

        List<ArticleInfo> articleInfos = articleResponse.getArticleInfos();
        if (articleInfos != null) {
            for (int i = 0; i < articleInfos.size(); i++) {
                ArticleApp articleApp = mapArticleResponseEntity(articleInfos.get(i), i);
                articles.add(articleApp);
            }
        }

        return articles;
    }

    @NonNull
    public List<ArticleApp> mapArticleDbEntities(@Nullable List<ArticleEntity> entities) {
        List<ArticleApp> articles = new ArrayList<>();
        if (entities == null || entities.isEmpty()) {
            return articles;
        }

        for (ArticleEntity entity : entities) {
            ArticleApp articleApp = mapArticleDbEntity(entity);
            articles.add(articleApp);
        }

        return articles;
    }

    @Nullable
    public List<ArticleEntity> mapAppEntities(List<ArticleApp> articles) {
        if (articles == null || articles.isEmpty()) {
            return null;
        }

        List<ArticleEntity> entities = new ArrayList<>();

        for (ArticleApp article : articles) {
            ArticleEntity entity = mapAppToDbEntity(article);

            entities.add(entity);
        }

        return entities;
    }

    @NonNull
    public ArticleEntity mapAppToDbEntity(ArticleApp article) {
        ArticleEntity entity = new ArticleEntity();
       // entity.setUid(article.getId());
        entity.setAuthor(article.getAuthor());
        entity.setDate(article.getDate());
        entity.setDescription(article.getDescription());
        entity.setTitle(article.getTitle());
        entity.setUrl(article.getUrl());
        entity.setUrlToImage(article.getUrlToImage());
        entity.setSourceApp(article.getSource());
        entity.setBookmarked(article.isBookmarked());
        return entity;
    }

    @NonNull
    private ArticleApp mapArticleDbEntity(ArticleEntity entity) {
        ArticleApp articleApp = new ArticleApp();
        articleApp.setId(entity.getUid()); // Id generated manually
        articleApp.setSource(mapDbSource(entity));
        articleApp.setTitle(entity.getTitle());
        articleApp.setAuthor(entity.getAuthor());
        articleApp.setDate(entity.getDate());
        articleApp.setDescription(entity.getDescription());
        articleApp.setUrl(entity.getUrl());
        articleApp.setUrlToImage(entity.getUrlToImage());
        articleApp.setBookmarked(entity.isBookmarked());
        return articleApp;
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
    private SourceApp mapDbSource(@Nullable ArticleEntity entity) {
        if (entity == null || entity.getSourceApp() == null) {
            return null;
        }

        SourceApp sourceApp = new SourceApp();
        sourceApp.setId(entity.getSourceApp().getId());
        sourceApp.setName(entity.getSourceApp().getName());
        return sourceApp;
    }

    /**
     * Articles from API response don't have an id's. So, let's generate them manually
     *
     * @param info     - object to map
     * @param position - id of the item
     */

    @NonNull
    private ArticleApp mapArticleResponseEntity(ArticleInfo info, int position) {
        ArticleApp articleApp = new ArticleApp();
       // articleApp.setId(position);
        articleApp.setSource(mapSource(info));
        articleApp.setTitle(info.getTitle());
        articleApp.setAuthor(info.getAuthor());
        articleApp.setDate(info.getDate());
        articleApp.setDescription(info.getDescription());
        articleApp.setUrl(info.getUrl());
        articleApp.setUrlToImage(info.getUrlToImage());
        return articleApp;
    }
}