package com.zhongyong.globalsearchtool.diy.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zhongyong.globalsearchtool.R
import com.zhongyong.globalsearchtool.databinding.ItemDiyBinding
import com.zhongyong.globalsearchtool.search.adapter.base.BaseRecycleAdapter
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import com.zhongyong.globalsearchtool.utils.AppUtils

/**
 *  自定义适配器
 */
class DiyAdapter(context: Context) : BaseRecycleAdapter<SearchInfo>(context){

    private var mContext = context;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DiyHolder(inflater.inflate(R.layout.item_diy, parent, false));
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as DiyHolder
        val info = list[position]
        holder.getBinding().info = list[position];

        Glide
            .with(mContext)
            .load(AppUtils.getIcon(mContext,info.packageId))
            .centerCrop()
            .into(holder.getBinding().itemDiyIcon);
        holder.getBinding().itemDiyClick.setOnClickListener(View.OnClickListener {
            // 查看具体信息

        })

        holder.getBinding().itemDiyClick.setOnLongClickListener({
            // 长按编辑及删除

            return@setOnLongClickListener true;
        })
    }

    class DiyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun getBinding(): ItemDiyBinding {
            return binding
        }

        private var binding: ItemDiyBinding;

        init {
            binding = DataBindingUtil.bind(itemView)!!
        }
    }
}