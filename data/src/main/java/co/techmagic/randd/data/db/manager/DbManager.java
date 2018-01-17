package co.techmagic.randd.data.db.manager;

import java.sql.SQLException;
import java.util.List;

import co.techmagic.randd.data.db.entity.ArticleEntity;

/**
 * Created by ruslankuziak on 12/28/17.
 */

public interface DbManager {

    void insertArticles(final List<ArticleEntity> entities) throws SQLException;

    List<ArticleEntity> getArticles() throws SQLException;

    void deleteAllFromTable() throws SQLException;

    void bookmarkArticle(ArticleEntity articleEntity) throws SQLException;
}