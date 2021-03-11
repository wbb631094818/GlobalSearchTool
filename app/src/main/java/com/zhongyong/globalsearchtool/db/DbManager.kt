package com.zhongyong.globalsearchtool.db

import android.util.Log
import androidx.room.Room
import com.zhongyong.globalsearchtool.application.SearchApplication
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import com.zhongyong.globalsearchtool.utils.AppUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.ArrayList

/**
 *  数据库的相关操作
 */
object DbManager {

    /**
     *  初始化数据库的数据
     */
    public fun initDbData() {
        GlobalScope.launch {
            val db = SearchApplication.getApplication()
                ?.let { Room.databaseBuilder(it, AppDatabase::class.java, "appinfo").build() }
            if (db?.SearchInfoDao()?.getCount() as Int <= 0) {
                Log.e("wbb", "getCount: 添加数据")
                val info = SearchApplication.getApplication()
                    ?.let { AppUtils.getPkgListNew(it) } as ArrayList<SearchInfo>
                db.SearchInfoDao().insertAll(info)
            }
        }
    }

    /**
     *  更新app数据
     */
    public suspend fun updateDbData(infos: ArrayList<SearchInfo>): ArrayList<SearchInfo> {
        // 获取现在本机上的APP数据
        val newInfos = SearchApplication.getApplication()
            ?.let { AppUtils.getPkgListNew(it) } as ArrayList<SearchInfo>
        if (newInfos.size != infos.size){
            Log.e("wbb", "updateDbData: 删除原有数据")
            // 删除原有数据，重新保存
            val db = SearchApplication.getApplication()
                ?.let { Room.databaseBuilder(it, AppDatabase::class.java, "appinfo").build() }
            db?.SearchInfoDao()?.deleteAll()
            db?.SearchInfoDao()?.insertAll(newInfos)
        }
        return newInfos;
    }
}