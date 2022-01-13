package com.zhongyong.globalsearchtool.utils

import com.tencent.mmkv.MMKV

object AppPreferencesUtils {

    public fun isOpenCase():Boolean{
      return MMKV.defaultMMKV()?.decodeBool("isOpenCase",true) == true
    }

    public fun setOpenCase(value:Boolean){
        MMKV.defaultMMKV()?.encode("isOpenCase",value)
    }


    public fun isOpenPinyin():Boolean{
       return MMKV.defaultMMKV()?.decodeBool("isOpenPinyin",true) == true
    }

    public fun setOpenPinyin(value:Boolean){
        MMKV.defaultMMKV()?.encode("isOpenPinyin",value)
    }


    public fun getDefultSearchEngine(): String? {
       return MMKV.defaultMMKV()?.decodeString("DefultSearchEngine","1")
    }

    public fun setDefultSearchEngine(value:String){
        MMKV.defaultMMKV()?.encode("DefultSearchEngine",value)
    }

    public fun getLastDefultBrowser(): String? {
        return MMKV.defaultMMKV()?.decodeString("LastDefultBrowser","")
    }

    public fun setLastDefultBrowser(value:String){
        MMKV.defaultMMKV()?.encode("LastDefultBrowser",value)
    }

    public fun isAppAutoUpdate(): Boolean{
        return MMKV.defaultMMKV()?.decodeBool("AppAutoUpdate",true) == true
    }

    public fun setAppAutoUpdate(value:Boolean){
        MMKV.defaultMMKV()?.encode("AppAutoUpdate",value)
    }

}