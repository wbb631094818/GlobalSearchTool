package com.zhongyong.globalsearchtool.db

import android.util.Log
import androidx.core.util.lruCache
import androidx.room.Room
import com.zhongyong.globalsearchtool.application.SearchApplication
import com.zhongyong.globalsearchtool.lrucache.CacheAppManager
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
                CacheAppManager.get()?.put("app",info)
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
        Log.e("wbb", "updateDbData: "+newInfos.size)
        if (newInfos.size != infos.size){
            Log.e("wbb", "updateDbData: 删除原有数据"+newInfos.size)
            // 删除原有数据，重新保存
            val db = SearchApplication.getApplication()
                ?.let { Room.databaseBuilder(it, AppDatabase::class.java, "appinfo").build() }
            db?.SearchInfoDao()?.deleteNoDiyAll()
            db?.SearchInfoDao()?.insertAll(newInfos)
            CacheAppManager.get()?.put("app",
                db?.SearchInfoDao()?.getAllAppData() as ArrayList<SearchInfo>
            )
        }
        return newInfos;
    }

    public suspend fun updateDbData(){
        // 获取现在本机上的APP数据
        val newInfos = SearchApplication.getApplication()
            ?.let { AppUtils.getPkgListNew(it) } as ArrayList<SearchInfo>
        Log.e("wbb", "updateDbData: "+newInfos.size)
        // 删除原有数据，重新保存
        val db = SearchApplication.getApplication()
            ?.let { Room.databaseBuilder(it, AppDatabase::class.java, "appinfo").build() }
        db?.SearchInfoDao()?.deleteNoDiyAll()
        db?.SearchInfoDao()?.insertAll(newInfos)
        CacheAppManager.get()?.put("app",newInfos)
    }

    public suspend fun getAllAppData():ArrayList<SearchInfo>{
        if (CacheAppManager.get()?.get("app")!=null){
           return CacheAppManager.get()?.get("app")!!
        }else{
            val info = SearchApplication.getApplication()?.let { Room.databaseBuilder(it, AppDatabase::class.java, "appinfo").build().SearchInfoDao().getAllAppData() } as ArrayList<SearchInfo>
            CacheAppManager.get()?.put("app",info)
            return info
        }
    }

    /**
     *  获取所有自定义数据
     */
    public suspend fun getAllDiyData():ArrayList<SearchInfo>{
        return SearchApplication.getApplication()?.let { Room.databaseBuilder(it, AppDatabase::class.java, "appinfo").build().SearchInfoDao().getAllDiyData() } as ArrayList<SearchInfo>
    }

    /**
     *  添加自定义数据
     */
    public suspend fun addDiyData(searchInfo: SearchInfo){
        SearchApplication.getApplication()?.let {
            Room.databaseBuilder(it, AppDatabase::class.java, "appinfo").build().SearchInfoDao().insert(searchInfo)
        }
        CacheAppManager.get()?.get("app")?.add(0,searchInfo)

    }

    suspend fun delDiyData(name: String){
        SearchApplication.getApplication()?.let {
            Room.databaseBuilder(it, AppDatabase::class.java, "appinfo").build().SearchInfoDao().deleteDiyData(name)
        }
    }
}