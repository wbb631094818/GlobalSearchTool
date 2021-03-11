package com.zhongyong.globalsearchtool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.tencent.mmkv.MMKV
import com.zhongyong.globalsearchtool.databinding.ActivityMainBinding
import com.zhongyong.globalsearchtool.db.AppDatabase
import com.zhongyong.globalsearchtool.search.SearchActivity
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import com.zhongyong.globalsearchtool.utils.AppUtils
import com.zhongyong.globalsearchtool.utils.MMKVStaticConstantUtils
import com.zhongyong.globalsearchtool.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding : ActivityMainBinding? = DataBindingUtil.setContentView(this,R.layout.activity_main)
        activityMainBinding?.bt?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,SearchActivity().javaClass);
            this.startActivity(intent)
        })


        if (MMKV.defaultMMKV()?.decodeBool(MMKVStaticConstantUtils.IS_CREATE_SHORTCUT) != true){
            Utils.addShortCutCompact(this);
        }

    }
}