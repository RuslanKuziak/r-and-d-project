package co.techmagic.randd.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import co.techmagic.randd.data.db.dao.ArticleDao;
import co.techmagic.randd.data.db.entity.ArticleEntity;

/**
 * Created by ruslankuziak on 12/27/17.
 */

@Database(entities = {ArticleEntity.class}, version = 1, exportSchema = false) // exportSchema - default is true
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract ArticleDao articleDao();

    public static AppDatabase getAppDatabase(@NonNull Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app-database.db")
                   // .allowMainThreadQueries() // TODO
                    .build();
        }

        return instance;
    }

    public static AppDatabase getInstance() {
        return instance;
    }

    public static void release() {
        instance = null;
    }
}