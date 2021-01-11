package com.blood.jetpackpaging3.paging.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.blood.jetpackpaging3.MainApplication;
import com.blood.jetpackpaging3.paging.bean.DataX;

@TypeConverters({Converters.class})
@Database(entities = {DataX.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "app.db";

    private static class Holder {
        private static final AppDatabase sInstance = Room.databaseBuilder(MainApplication.getContext(), AppDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration() // 升级, 当未匹配到版本的时候就会直接删除表然后重新创建。
                .allowMainThreadQueries()
                .build();
    }

    public static AppDatabase getInstance() {
        return Holder.sInstance;
    }

    public abstract ArticleDao getArticleDao();

}
