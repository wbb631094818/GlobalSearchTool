package com.zhongyong.globalsearchtool.diy.adapter

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zhongyong.globalsearchtool.R
import com.zhongyong.globalsearchtool.databinding.ItemDiyBinding
import com.zhongyong.globalsearchtool.db.DbManager
import com.zhongyong.globalsearchtool.diy.dialog.AddDiyDialog
import com.zhongyong.globalsearchtool.search.adapter.base.BaseRecycleAdapter
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import com.zhongyong.globalsearchtool.utils.AppUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *  自定义适配器
 */
class DiyAdapter(context: Context) : BaseRecycleAdapter<SearchInfo>(context) {

    private var mContext = context;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return DiyHolder(inflater.inflate(R.layout.item_diy, parent, false));
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as DiyHolder
        var info = list[holder.getAdapterPosition()]
        val i = position;
        holder.getBinding().info = list[i];

        Glide
            .with(mContext)
            .load(AppUtils.getIcon(mContext, info.packageId))
            .centerCrop()
            .into(holder.getBinding().itemDiyIcon);
        holder.getBinding().itemDiyClick.setOnClickListener(View.OnClickListener {
            // 查看具体信息

        })

        holder.getBinding().itemDiyClick.setOnLongClickListener({
            val array: Array<String> = Array(2,{""});
            array[0] = mContext.getString(R.string.editor)
            array[1] = mContext.getString(R.string.delete)
            // 长按编辑及删除
            AlertDialog.Builder(mContext)
                .setItems(array, DialogInterface.OnClickListener { dialog, which ->
                    if (array[1].equals(array[which])){
                        // 删除
                        AlertDialog.Builder(mContext).setMessage(mContext.getString(R.string.delete_this_data)).setNegativeButton(mContext.getString(
                                                    R.string.cancel),null)
                            .setPositiveButton(mContext.getString(R.string.delete), DialogInterface.OnClickListener{dialog,which ->
                                GlobalScope.launch(Dispatchers.IO) {
                                    DbManager.delDiyData(info.name)
                                    DbManager.updateDbData()
                                }.invokeOnCompletion {
                                    GlobalScope.launch(Dispatchers.Main) {
                                        list.remove(info)
                                        notifyDataSetChanged()
                                        dialog.dismiss();
                                    }
                                }
                            }).show()
                    }else if (array[0].equals(array[which])){
                        // 去编辑
                        AddDiyDialog.showEditDialog(mContext,info,object :AddDiyDialog.AddDialogCallBack{
                            override fun add(searchInfo: SearchInfo) {
                                list[i] = searchInfo;
                                notifyDataSetChanged();
                            }

                        })
                    }
                    dialog.dismiss()
                }).show()
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