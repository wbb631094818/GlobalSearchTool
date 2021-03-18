package com.zhongyong.globalsearchtool.diy

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhongyong.globalsearchtool.R
import com.zhongyong.globalsearchtool.databinding.ActivityDiyBinding
import com.zhongyong.globalsearchtool.db.DbManager
import com.zhongyong.globalsearchtool.diy.adapter.DiyAdapter
import com.zhongyong.globalsearchtool.diy.dialog.AddDiyDialog
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *  用户可设置自定义跳转的网址
 */
class DiyActivity : AppCompatActivity() {

    private lateinit var activityDiyBinding: ActivityDiyBinding;
    private lateinit var diyAdapter: DiyAdapter;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDiyBinding = DataBindingUtil.setContentView(this, R.layout.activity_diy);

        val linearLayoutManager = LinearLayoutManager(this);
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        activityDiyBinding.diyRlv.layoutManager = linearLayoutManager
        diyAdapter = DiyAdapter(this);
        activityDiyBinding.diyRlv.adapter = diyAdapter;

        activityDiyBinding.diyBack.setOnClickListener({
            finish()
        })

        activityDiyBinding.diyAdd.setOnClickListener({
            // 添加数据
            AddDiyDialog.showDialog(this)
        })

        getAllAppInfo()
    }

    private fun getAllAppInfo() {
        var infos: ArrayList<SearchInfo> = arrayListOf();
        lifecycleScope.launch(Dispatchers.IO) {
            infos = DbManager.getAllDiyData();
        }.invokeOnCompletion {
            lifecycleScope.launch(Dispatchers.Main) {
                diyAdapter.addDataList(infos, true)
                diyAdapter.notifyDataSetChanged()
            }
        }
    }


}