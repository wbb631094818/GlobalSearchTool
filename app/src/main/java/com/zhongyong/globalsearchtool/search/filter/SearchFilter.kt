package com.zhongyong.globalsearchtool.search.filter

import android.util.Log
import android.widget.Filter
import com.zhongyong.globalsearchtool.search.`interface`.SearchFilterInterface
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import com.zhongyong.globalsearchtool.search.manager.SearchManager
import com.zhongyong.globalsearchtool.utils.AppPreferencesUtils
import java.util.*
import kotlin.collections.ArrayList


/**
 *  搜索过滤器
 */
class SearchFilter(infos: ArrayList<SearchInfo>, callBack: SearchFilterInterface) : Filter() {
    // 源数据
    var infos: ArrayList<SearchInfo>;

    // 回调
    var callBack: SearchFilterInterface;

    init {
        this.infos = infos;
        this.callBack = callBack;
    }


    override fun performFiltering(constraint: CharSequence?): FilterResults {
        var filterStr = constraint.toString()
        val mFilterBeans: ArrayList<SearchInfo> = ArrayList();
        val isOpenCase = AppPreferencesUtils.isOpenCase()
        val isOpenPinyin = AppPreferencesUtils.isOpenPinyin()
        if (!filterStr.isEmpty()) {
            mFilterBeans.clear()
            for (bean in infos) {
                //如果名称包括搜索字段
                var name = bean.name;
                // 是否区分大小写
                if (isOpenCase) {
                    name = name.toLowerCase(Locale.CHINA);
                    filterStr = filterStr.toLowerCase(Locale.CHINA);
                }
                if (name.contains(filterStr) || (isOpenPinyin && bean.pinyin.contains(filterStr.toUpperCase(Locale.CHINA)))) {
                    mFilterBeans.add(bean)
                }
            }
            if (mFilterBeans.size <= 0) {
                Log.e("wbb", "performFiltering: =================")
                SearchManager.addWebSearch(filterStr, mFilterBeans)
            }
        }
        val results = FilterResults()
        results.values = mFilterBeans
        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        this.callBack.publishResults(constraint, results?.values as ArrayList<SearchInfo>)
    }
}