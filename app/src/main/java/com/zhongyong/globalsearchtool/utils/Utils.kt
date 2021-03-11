package com.zhongyong.globalsearchtool.utils

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
        if (imm != null) {
            view.requestFocus()
            imm.showSoftInput(view, 0)
        }
    }
}