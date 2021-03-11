package com.zhongyong.globalsearchtool.search

import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.zhongyong.globalsearchtool.R
import com.zhongyong.globalsearchtool.databinding.ActivitySearchBinding
import com.zhongyong.globalsearchtool.db.AppDatabase
import com.zhongyong.globalsearchtool.db.DbManager.updateDbData
import com.zhongyong.globalsearchtool.receiver.AppBootReceiver
import com.zhongyong.globalsearchtool.search.`interface`.SearchFilterInterface
import com.zhongyong.globalsearchtool.search.adapter.SearchAdapter
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import com.zhongyong.globalsearchtool.search.filter.SearchFilter
import com.zhongyong.globalsearchtool.search.manager.SearchManager
import com.zhongyong.globalsearchtool.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class SearchActivity: AppCompatActivity() , Filterable {

    private var searchAdapter: SearchAdapter? = null;
    // 所有的APP数据
    private var info = ArrayList<SearchInfo>();

    // App 安装卸载监听广播
//    private lateinit var installedReceiver:AppBootReceiver;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.statusBarColor = ContextCompat.getColor(this, R.color.transparent_20)
        val searchBinding:ActivitySearchBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_search
        );
        searchBinding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //输入后的监听
                Log.e(
                    "wbb",
                    "beforeTextChanged: CharSequence：" + s + "  start:" + start + " after:" + after
                )
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //输入文字产生变化的监听
                Log.e(
                    "wbb",
                    "onTextChanged: CharSequence：" + s + "  before:" + before + " count:" + count
                )
            }

            override fun afterTextChanged(s: Editable?) {
                //输入文字产生变化的监听
                getFilter().filter(s)
            }
        })

        // 点击确定，选中第一个
        searchBinding.searchEt.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
               val searchInfos:ArrayList<SearchInfo>? = searchAdapter?.getAdapterData();
                if (searchInfos != null) {
                    if (searchInfos.size>0){
                        SearchManager.searchItemClick(this, searchInfos[0])
                        return@OnEditorActionListener false
                    }
                }
            }
            true
        })

        val linearLayoutManager = LinearLayoutManager(this);
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        searchBinding.searchRlv.layoutManager = linearLayoutManager
        searchAdapter = SearchAdapter(this);
        searchBinding.searchRlv.adapter = searchAdapter;

        searchBinding.searchBg.setOnClickListener({
            finish();
        })

        // 展示软键盘
        Utils.showKeyboard(searchBinding.searchEt)

        getAllAppInfo()
    }

    override fun onStart() {
        super.onStart()
//        // 注册监听APP安装卸载的广播
//        installedReceiver =  AppBootReceiver();
//        val filter = IntentFilter();
//        filter.addAction("android.intent.action.PACKAGE_ADDED");
//        filter.addAction("android.intent.action.PACKAGE_REMOVED");
//        filter.addDataScheme("package");
//        this.registerReceiver(installedReceiver, filter);

    }

    override fun onDestroy() {
        super.onDestroy()
//        // 注销广播
//        installedReceiver.let {
//            this.unregisterReceiver(it)
//        }
    }


    private fun getAllAppInfo(){
        GlobalScope.launch {
            Log.e("wbb", "开始: ")
            info = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "appinfo").build().SearchInfoDao().loadAll() as ArrayList<SearchInfo>
            Log.e("wbb", "结束")
            info = updateDbData(info)
        }.invokeOnCompletion {
            // 子线程
            Log.e("wbb", "invokeOnCompletion: ")
            Handler(Looper.getMainLooper()).post {
                Log.e("wbb", "size: " + info.size)
            }
        }


    }

    override fun getFilter(): Filter {
        return SearchFilter(info, object : SearchFilterInterface {
            override fun publishResults(constraint: CharSequence?, results: ArrayList<SearchInfo>) {
                super.publishResults(constraint, results)
                searchAdapter?.addDataList(results, true)
                searchAdapter?.notifyDataSetChanged()
            }
        })
    }
}