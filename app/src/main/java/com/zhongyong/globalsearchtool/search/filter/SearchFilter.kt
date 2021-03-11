package com.zhongyong.globalsearchtool.search.filter

import android.util.Log
import android.widget.Filter
import com.zhongyong.globalsearchtool.search.`interface`.SearchFilterInterface
import com.zhongyong.globalsearchtool.search.bean.SearchInfo


/**
 *  搜索过滤器
 */
class SearchFilter(infos: ArrayList<SearchInfo>, callBack: SearchFilterInterface):Filter(){
    // 源数据
    var infos:ArrayList<SearchInfo>;
    // 回调
    var callBack:SearchFilterInterface;

    init {
        this.infos = infos;
        this.callBack = callBack;
    }


    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val filterStr = constraint.toString()
        val mFilterBeans:ArrayList<SearchInfo> = ArrayList();
        if (!filterStr.isEmpty()) {
            mFilterBeans.clear()
            for (bean in infos) {
                //如果名称包括搜索字段
                Log.e("wbb", "filterStr: "+filterStr.toUpperCase())
                if (bean.name.toLowerCase().contains(filterStr.toLowerCase()) || bean.pinyin.contains(filterStr.toUpperCase())) {
                    mFilterBeans.add(bean)
                }
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