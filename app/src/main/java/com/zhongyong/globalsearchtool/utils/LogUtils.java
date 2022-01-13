package com.zhongyong.globalsearchtool.utils;

import android.util.Log;

import com.zhongyong.globalsearchtool.BuildConfig;

public class LogUtils {

    public static void e(String str){
        if (BuildConfig.DEBUG){
            Log.e("wbb", str);
        }
    }
}
