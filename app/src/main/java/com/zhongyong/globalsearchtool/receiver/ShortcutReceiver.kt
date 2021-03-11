package com.zhongyong.globalsearchtool.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tencent.mmkv.MMKV
import com.zhongyong.globalsearchtool.utils.MMKVStaticConstantUtils


/**
 *  桌面快捷方式点击创建回调
 */
class ShortcutReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // 桌面快捷方式生成成功监听
        MMKV.defaultMMKV()?.encode(MMKVStaticConstantUtils.IS_CREATE_SHORTCUT,true);
    }
}