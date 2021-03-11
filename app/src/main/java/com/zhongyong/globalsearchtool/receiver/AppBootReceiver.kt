package com.zhongyong.globalsearchtool.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AppBootReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        TODO("Not yet implemented")
        if ("android.intent.action.PACKAGE_ADDED".equals(intent?.getAction())) {
            // app 安装广播
        }

        if ("android.intent.action.PACKAGE_REMOVED".equals(intent?.getAction())) {
            // app 卸载广播

        }
    }
}