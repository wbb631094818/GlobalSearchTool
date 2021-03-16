package com.zhongyong.globalsearchtool.utils

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.zhongyong.globalsearchtool.R
import com.zhongyong.globalsearchtool.receiver.ShortcutReceiver
import com.zhongyong.globalsearchtool.search.SearchActivity


object Utils {

    /**
     *  创建快捷方式
     */
    fun addShortCutCompact(context: Context) {
        if (ShortcutManagerCompat.isRequestPinShortcutSupported(context)) {
            val shortcutInfoIntent = Intent(context, SearchActivity::class.java)
            shortcutInfoIntent.action = Intent.ACTION_VIEW //action必须设置，不然报错
            val info = ShortcutInfoCompat.Builder(context, "The only id")
                .setIcon(IconCompat.createWithResource(context, R.mipmap.ic_launcher))
                .setShortLabel("全局搜索")
                .setIntent(shortcutInfoIntent)
                .build()

            //当添加快捷方式的确认弹框弹出来时，将被回调
            val shortcutCallbackIntent = PendingIntent.getBroadcast(
                context, 0, Intent(
                    context,
                    ShortcutReceiver::class.java
                ), PendingIntent.FLAG_UPDATE_CURRENT
            )
            ShortcutManagerCompat.requestPinShortcut(
                context,
                info, shortcutCallbackIntent.intentSender
            )
        }
    }

    fun showKeyboard(view: View) {
        val imm: InputMethodManager = view.getContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view.requestFocus()
        imm.showSoftInput(view, 0)
    }

    fun hideKeyBoard(view: View) {
        val imm: InputMethodManager = view.getContext()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view.requestFocus()
        val activity = view.context as Activity;
        imm.hideSoftInputFromWindow(activity.getCurrentFocus()?.getWindowToken(),
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    /**
     *  退回桌面
     */
    fun gotoDeskTop(context: Context){
        val home = Intent(Intent.ACTION_MAIN)
        home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        home.addCategory(Intent.CATEGORY_HOME)
        context.startActivity(home)
    }

    /**
     *  返回选择的默认搜索引擎
     */
    fun getSearchWebUrl(str:String?,searchText:String):String{
        if ("谷歌".equals(str)){
            return "https://www.google.com.hk/search?q="+searchText;
        }else if ("必应".equals(str)){
            return "https://cn.bing.com/search?q="+searchText;
        }else if ("搜狗".equals(str)){
            return "https://www.so.com/s?q="+searchText
        }else{
          // 百度
           return "https://m.baidu.com/s?word="+searchText;
        }
    }
}