package com.zhongyong.globalsearchtool.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhongyong.globalsearchtool.db.search.SearchInfoDao
import com.zhongyong.globalsearchtool.search.bean.SearchInfo


@Database(version = 1, entities = [SearchInfo::class])
abstract class AppDatabase: RoomDatabase(){

    abstract public fun SearchInfoDao():SearchInfoDao;

}