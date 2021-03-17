package com.zhongyong.globalsearchtool.diy.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.zhongyong.globalsearchtool.R
import com.zhongyong.globalsearchtool.databinding.DialogAddDiyBinding
import com.zhongyong.globalsearchtool.search.bean.SearchInfo

object AddDiyDialog {


    fun showDialog(context: Context) {
        val binding: DialogAddDiyBinding = DataBindingUtil.bind(
            LayoutInflater.from(context).inflate(R.layout.dialog_add_diy, null, false)
        )!!
        val searchInfo = SearchInfo()
        binding.info = searchInfo
        AlertDialog.Builder(context).setView(binding.root).show()
        binding.diyAddClick.setOnClickListener({
            // 点击添加

        })
    }

}