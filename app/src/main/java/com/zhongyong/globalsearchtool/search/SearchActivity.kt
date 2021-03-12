package com.zhongyong.globalsearchtool.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.zhongyong.globalsearchtool.R
import com.zhongyong.globalsearchtool.db.AppDatabase
import com.zhongyong.globalsearchtool.db.DbManager.updateDbData
import com.zhongyong.globalsearchtool.search.`interface`.SearchFilterInterface
import com.zhongyong.globalsearchtool.search.adapter.SearchAdapter
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import com.zhongyong.globalsearchtool.search.filter.SearchFilter
import com.zhongyong.globalsearchtool.search.manager.SearchManager
import com.zhongyong.globalsearchtool.setting.SettingActivity
import com.zhongyong.globalsearchtool.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class SearchActivity: AppCompatActivity() , Filterable {

    private var searchAdapter: SearchAdapter? = null;
    private var searchEt:EditText? = null;
    // 所有的APP数据
    private var info = ArrayList<SearchInfo>();

    // App 安装卸载监听广播
//    private lateinit var installedReceiver:AppBootReceiver;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.statusBarColor = ContextCompat.getColor(this, R.color.transparent_20)
        setContentView(R.layout.activity_search)
        searchEt = findViewById<EditText>(R.id.search_et);
        val searchRlv = findViewById<RecyclerView>(R.id.search_rlv);
        val search_bg = findViewById<RelativeLayout>(R.id.search_bg);
        val setting_click = findViewById<ImageView>(R.id.setting_click);
        searchEt?.addTextChangedListener(object : TextWatcher {
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
        searchEt?.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
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

        setting_click.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,SettingActivity().javaClass);
            this.startActivity(intent)
        })

        val linearLayoutManager = LinearLayoutManager(this);
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        searchRlv.layoutManager = linearLayoutManager
        searchAdapter = SearchAdapter(this);
        searchRlv.adapter = searchAdapter;

        search_bg.setOnClickListener({
            finish();
        })

        getAllAppInfo()
    }

    override fun onStart() {
        super.onStart()
        // 展示软键盘
        searchEt?.let { Utils.showKeyboard(it) }
    }

    override fun onStop() {
        super.onStop()
        searchEt?.let { Utils.hideKeyBoard(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
//        // 注销广播
    }


    private fun getAllAppInfo(){
        GlobalScope.launch {
            Log.e("wbb", "开始: ")
            info = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "appinfo").build().SearchInfoDao().loadAll() as ArrayList<SearchInfo>
            Log.e("wbb", "结束")
            info = updateDbData(info)
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