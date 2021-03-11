package com.zhongyong.globalsearchtool.search.`interface`

import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import java.util.*

interface SearchFilterInterface {
    /**
     *  过滤完后的数据
     */
    fun publishResults(constraint: CharSequence?, results: ArrayList<SearchInfo>) {}

}