package com.blood.jetpackpaging3.paging.bean

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

data class ArticleResult(
    val data: Data,
    val errorCode: Int,
    val errorMsg: String
)

data class Data(
    val curPage: Int,
    val datas: List<DataX>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

@Entity(tableName = "articles"/*, indices = [Index("id", unique = true)]*/)
data class DataX(
    @PrimaryKey(autoGenerate = true)
    val db_id: Int,
    val apkLink: String,
    val audit: Int,
    val author: String,
    val canEdit: Boolean,
    val chapterId: Int,
    val chapterName: String,
    val collect: Boolean,
    val courseId: Int,
    val desc: String,
    val descMd: String,
    val envelopePic: String,
    val fresh: Boolean,
    val host: String,
    val id: Int,
    val link: String,
    val niceDate: String,
    val niceShareDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: Long,
    val realSuperChapterId: Int,
    val selfVisible: Int,
    val shareDate: Long,
    val shareUser: String,
    val superChapterId: Int,
    val superChapterName: String,
    val tags: List<Tag>,
    val title: String,
    val type: Int,
    val userId: Int,
    val visible: Int,
    val zan: Int,
    // 添加一个分页属性
    var page: Int = 0
)

data class Tag(
    val name: String,
    val url: String
)

/*
{
apkLink: "",
audit: 1,
author: "jenly1314",
canEdit: false,
chapterId: 539,
chapterName: "未分类",
collect: false,
courseId: 13,
desc: "一个专注于 Android 视频播放器的基础库，支持无缝切换内核。（IjkPlayer、ExoPlayer、VlcPlayer、MediaPlayer）",
descMd: "",
envelopePic: "https://www.wanandroid.com/resources/image/pc/default_project_img.jpg",
fresh: false,
host: "",
id: 16699,
link: "https://www.wanandroid.com/blog/show/2875",
niceDate: "2021-01-01 00:22",
niceShareDate: "2021-01-01 00:22",
origin: "",
prefix: "",
projectLink: "https://github.com/jenly1314/KingPlayer",
publishTime: 1609431744000,
realSuperChapterId: 293,
selfVisible: 0,
shareDate: 1609431744000,
shareUser: "",
superChapterId: 294,
superChapterName: "开源项目主Tab",
tags: [
{
name: "项目",
url: "/project/list/1?cid=539"
}
],
title: "KingPlayer一个专注于 Android 视频播放器的基础库，支持无缝切换内核。",
type: 0,
userId: -1,
visible: 1,
zan: 0
}
*/