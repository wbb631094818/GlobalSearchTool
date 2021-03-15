package com.zhongyong.globalsearchtool.lrucache

import android.util.LruCache
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import java.util.*

/**
 *  缓存App管理
 */
class CacheAppManager {
    companion object{
        private var appCache: CacheAppManager? = null
        fun get(): CacheAppManager? {
            if (appCache == null) {
                appCache = CacheAppManager()
            }
            return appCache
        }
    }

    private var cache: LruCache<String, ArrayList<SearchInfo>>? = null

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        cache = LruCache(maxMemory / 8)
    }

    operator fun get(key: String): ArrayList<SearchInfo>? {
        return cache!!.get(key)
    }

    fun put(key: String, infos: ArrayList<SearchInfo>) {
        cache!!.put(key, infos)
    }
}