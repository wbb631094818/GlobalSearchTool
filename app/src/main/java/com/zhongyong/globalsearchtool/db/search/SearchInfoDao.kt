package com.zhongyong.globalsearchtool.db.search

import androidx.room.*
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import java.util.ArrayList


@Dao
public interface SearchInfoDao {

    @Query("SELECT * FROM appinfo")
    suspend fun loadAll():List<SearchInfo>;

    @Query("SELECT * FROM APPINFO WHERE isDiy = 1")
    suspend fun getAllDiyData():List<SearchInfo>

    @Query("SELECT * FROM APPINFO ORDER BY isDiy DESC")
    suspend fun getAllAppData():List<SearchInfo>

    @Insert(entity = SearchInfo::class)
    suspend fun insertAll(searchInfo: ArrayList<SearchInfo>)

    @Insert(entity = SearchInfo::class)
    suspend fun insert(searchInfo: SearchInfo)

    @Delete(entity = SearchInfo::class)
    suspend fun delete(searchInfo: SearchInfo)

    @Query("DELETE FROM APPINFO WHERE packageId = :packageId")
    suspend fun delete(packageId:String)

    @Query("DELETE FROM APPINFO WHERE name = :name And isDiy = 1")
    suspend fun deleteDiyData(name:String)

    @Query("delete from appinfo")
    suspend fun deleteAll()

    @Query("delete from appinfo WHERE isDiy = 0")
    suspend fun deleteNoDiyAll()

    @Update
    suspend fun update(searchInfo: SearchInfo)

    @Query("SELECT count(*) FROM APPINFO")
    suspend fun getCount():Int

}