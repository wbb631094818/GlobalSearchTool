package com.zhongyong.globalsearchtool.search.adapter.base

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 *  自定义通用父类RecycleAdapter
 */
public abstract class BaseRecycleAdapter<T>(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    protected var list:ArrayList<T>;
    protected var inflater: LayoutInflater;

    init {
        this.list = ArrayList();
        this.inflater = LayoutInflater.from(context)
    }

    /**
     * 添加数据到头部
     */
    fun addDataToTop(t: T, isclear: Boolean) {
        if (t != null) {
            if (isclear) {
                list.clear()
            }
            list.add(0, t)
        }
    }

    override fun getItemCount(): Int {
        return if (list != null) {
            list.size
        } else 0
    }

    /**
     * 获取数据集合
     * @return 返回该集合
     */
    fun getAdapterData(): ArrayList<T>{
        return list
    }

    /**
     * 返回数据集合的数量
     * @return 数量
     */
    fun getListSize(): Int {
        return if (list != null) {
            list.size
        } else 0
    }

    /**
     * 清除所有数据
     */
    fun clearAll() {
        if (list != null) {
            list.clear()
        }
    }

    /**
     * 删除指定数据
     * @param i
     */
    fun clearData(i: Int) {
        if (list != null && list.size > i) {
            list.removeAt(i)
        }
    }

    /**
     * 添加数据到头部
     */
    fun addDataTop(t: T, isclear: Boolean) {
        if (t != null) {
            if (isclear) {
                list.clear()
            }
            list.add(0, t)
        }
    }

    /**
     * 添加新数据集合到头部
     *
     * @param newlist 新数据集合
     * @param isclear 是否清空
     */
    fun addDataListTop(newlist: ArrayList<T>, isclear: Boolean) {
        if (newlist != null) {
            if (isclear) {
                list.clear()
            }
            list.addAll(0, newlist)
        }
    }

    /**
     * 添加新数据集合
     *
     * @param newlist
     * @param isclear
     */
    fun addDataList(newlist: ArrayList<T>, isclear: Boolean) {
        if (newlist != null) {
            if (isclear) {
                list.clear()
            }
            list.addAll(newlist)
        }
    }

    /**
     * 添加新数据集合
     *
     * @param newlist
     * @param isclear
     */
    fun addDataList(newlist: ArrayList<T>, i: Int, isclear: Boolean) {
        if (newlist != null) {
            if (isclear) {
                list.clear()
            }
            if (list.size > 0) {
                list.addAll(i, newlist)
            } else {
                list.addAll(newlist)
            }
        }
    }

    /**
     * 添加一个数据
     *
     * @param t
     * @param isclear
     */
    fun appendData(t: T, isclear: Boolean) {
        if (t == null) {
            return
        }
        if (isclear) {
            list.clear()
        }
        list.add(t)
    }

    /**
     * 添加一个数据到头部
     * @param t 该数据
     * @param isclear 是否删除
     */
    fun appendDataTop(t: T, isclear: Boolean) {
        if (t == null) {
            return
        }
        if (isclear) {
            list.clear()
        }
        list.add(0, t)
    }

    /**
     * 添加一个数据
     * @param t 该数据
     * @param isclear 是否删除
     */
    fun appendData(t: T, i: Int, isclear: Boolean) {
        if (t == null) {
            return
        }
        if (isclear) {
            list.clear()
        }
        if (list.size > i) {
            list.add(i, t)
        } else {
            list.add(t)
        }
    }
}