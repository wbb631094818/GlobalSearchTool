package com.zhongyong.globalsearchtool.search

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.zhongyong.globalsearchtool.R
import com.zhongyong.globalsearchtool.db.AppDatabase
import com.zhongyong.globalsearchtool.db.DbManager
import com.zhongyong.globalsearchtool.db.DbManager.updateDbData
import com.zhongyong.globalsearchtool.lrucache.CacheAppManager
import com.zhongyong.globalsearchtool.search.`interface`.SearchFilterInterface
import com.zhongyong.globalsearchtool.search.adapter.SearchAdapter
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import com.zhongyong.globalsearchtool.search.filter.SearchFilter
import com.zhongyong.globalsearchtool.search.manager.SearchManager
import com.zhongyong.globalsearchtool.setting.SettingActivity
import com.zhongyong.globalsearchtool.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class SearchActivity : AppCompatActivity(), Filterable {

    private var searchAdapter: SearchAdapter? = null;
    private var searchEt: EditText? = null;

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
                val searchInfos: ArrayList<SearchInfo>? = searchAdapter?.getAdapterData();
                if (searchInfos != null) {
                    if (searchInfos.size > 0) {
                        searchEt?.text = Editable.Factory.getInstance().newEditable("")
                        SearchManager.searchItemClick(this, searchInfos[0])
                        return@OnEditorActionListener false
                    }
                }
            }
            true
        })

        setting_click.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SettingActivity().javaClass);
            this.startActivity(intent)
        })

        val linearLayoutManager = LinearLayoutManager(this);
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        searchRlv.layoutManager = linearLayoutManager
        searchAdapter = SearchAdapter(this);
        searchRlv.adapter = searchAdapter;
        searchEt?.let {
            it.requestFocus()
        }


        search_bg.setOnClickListener({
            searchEt?.text = Editable.Factory.getInstance().newEditable("")
            Utils.gotoDeskTop(this)
        })

        getAllAppInfo()

    }


    private fun getAllAppInfo() {
        lifecycleScope.launch(Dispatchers.IO) {
            Log.e("wbb", "开始: ")
            info = DbManager.getAllAppData();
            Log.e("wbb", "结束")
            info = updateDbData(info)
        }
        Log.e("wbb", "getAllAppInfo:================= ")
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            searchEt?.text = Editable.Factory.getInstance().newEditable("")
            Utils.gotoDeskTop(this)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


}