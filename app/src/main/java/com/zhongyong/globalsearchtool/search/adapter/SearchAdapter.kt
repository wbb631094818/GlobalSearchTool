package com.zhongyong.globalsearchtool.search.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zhongyong.globalsearchtool.R
import com.zhongyong.globalsearchtool.databinding.ItemSearchBinding
import com.zhongyong.globalsearchtool.search.adapter.base.BaseRecycleAdapter
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import com.zhongyong.globalsearchtool.search.manager.SearchManager
import com.zhongyong.globalsearchtool.utils.AppUtils

/**
 *  搜索适配器
 */
public class SearchAdapter(context: Context) : BaseRecycleAdapter<SearchInfo>(context){

    private var mContext = context;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchHolder(inflater.inflate(R.layout.item_search, parent, false));
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as SearchHolder
        val info = list[position]
        holder.getBinding().info = list[position];

        Glide
            .with(mContext)
            .load(AppUtils.getIcon(mContext,info.packageId))
            .centerCrop()
            .into(holder.getBinding().itemSearchIcon);
        holder.getBinding().itemSearchClick.setOnClickListener(View.OnClickListener {
            // 点击跳转
            SearchManager.searchItemClick(this.mContext,info)
        })

    }


    class SearchHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun getBinding(): ItemSearchBinding {
            return binding
        }

        private var binding: ItemSearchBinding;

        init {
            binding = DataBindingUtil.bind(itemView)!!
        }
    }


}