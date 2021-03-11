package com.zhongyong.globalsearchtool.db.search

import androidx.room.*
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import java.util.ArrayList


@Dao
public interface SearchInfoDao {

    @Query("SELECT * FROM appinfo")
    suspend fun loadAll():List<SearchInfo>;

    @Insert(entity = SearchInfo::class)
    suspend fun insertAll(searchInfo: ArrayList<SearchInfo>)

    @Delete(entity = SearchInfo::class)
    suspend fun delete(searchInfo: SearchInfo)

    @Query("DELETE FROM APPINFO WHERE packageId = :packageId")
    suspend fun delete(packageId:String)

    @Query("delete from appinfo")
    suspend fun deleteAll()

    @Update
    suspend fun update(searchInfo: SearchInfo)

    @Query("SELECT count(*) FROM APPINFO")
    suspend fun getCount():Int

}