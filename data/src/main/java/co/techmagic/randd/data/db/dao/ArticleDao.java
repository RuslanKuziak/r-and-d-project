package co.techmagic.randd.data.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import co.techmagic.randd.data.db.entity.ArticleEntity;

/**
 * Created by ruslankuziak on 12/27/17.
 */

@Dao
public interface ArticleDao {

    @Query("SELECT * from article")
    List<ArticleEntity> getAll();

    @Query("DELETE from article")
    void deleteFromTable();

    @Insert
    void insertArticles(ArticleEntity... articles);

    @Delete
    void delete(ArticleEntity article);

    @Query("UPDATE article SET bookmarked = :isBookmarked WHERE uid = :articleId")
    void bookmarkArticle(int articleId, boolean isBookmarked);
}