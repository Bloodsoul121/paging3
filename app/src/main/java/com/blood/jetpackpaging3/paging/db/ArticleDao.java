package com.blood.jetpackpaging3.paging.db;

import androidx.paging.PagingSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.blood.jetpackpaging3.paging.bean.DataX;

import java.util.List;

// 访问数据库操作的接口 Database access object

@Dao
public interface ArticleDao {

    //我们所有对数据库的操作都在Dao里声明

    // 这些修饰词会在编译时候生成代码
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticles(List<DataX> Articles);

    @Query("DELETE FROM articles")
    void deleteArticles();

    // 获取所有的Article，并且按照uid降序排序
    @Query("SELECT * FROM articles ORDER BY shareDate ASC limit :num offset :offset")
    PagingSource<Integer, DataX> queryArticles(int offset, int num);

    @Query("SELECT * FROM articles")
    PagingSource<Integer, DataX> queryArticles();

}
