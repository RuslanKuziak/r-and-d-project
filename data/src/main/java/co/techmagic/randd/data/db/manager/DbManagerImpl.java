package co.techmagic.randd.data.db.manager;

import java.sql.SQLException;
import java.util.List;

import co.techmagic.randd.data.db.AppDatabase;
import co.techmagic.randd.data.db.entity.ArticleEntity;

/**
 * Created by ruslankuziak on 12/28/17.
 */

public class DbManagerImpl implements DbManager {

    private AppDatabase appDatabase;

    public DbManagerImpl() {
        appDatabase = AppDatabase.getInstance();
    }

    @Override
    public void insertArticles(final List<ArticleEntity> entities) throws SQLException {
        appDatabase.articleDao().insertArticles(entities.toArray(new ArticleEntity[entities.size()]));
    }

    @Override
    public List<ArticleEntity> getArticles() {
        return appDatabase.articleDao().getAll();
    }

    @Override
    public void deleteAllFromTable() throws SQLException {
        appDatabase.articleDao().deleteFromTable();
    }

    @Override
    public void bookmarkArticle(ArticleEntity articleEntity) {
        appDatabase.articleDao().bookmarkArticle(articleEntity.getUrl(), articleEntity.isBookmarked());
    }
}