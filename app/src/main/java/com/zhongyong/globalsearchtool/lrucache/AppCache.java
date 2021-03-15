package com.zhongyong.globalsearchtool.lrucache;

import android.util.LruCache;

import com.zhongyong.globalsearchtool.search.bean.SearchInfo;

import java.util.ArrayList;

public class AppCache {

    private AppCache appCache = null;
    public AppCache get(){
        if (appCache == null){
            appCache = new AppCache();
        }
        return appCache;
    }

    private LruCache<String, ArrayList<SearchInfo>> cache = null;


    private AppCache(){
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        cache = new LruCache<>(maxMemory / 8);
    }

    public ArrayList<SearchInfo> get(String key){
        return cache.get(key);
    }

    public void put(String key,ArrayList<SearchInfo> infos){
        cache.put(key,infos);
    }
}
