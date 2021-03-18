package com.zhongyong.globalsearchtool.diy.dialog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.zhongyong.globalsearchtool.R
import com.zhongyong.globalsearchtool.databinding.DialogAddDiyBinding
import com.zhongyong.globalsearchtool.db.DbManager
import com.zhongyong.globalsearchtool.search.bean.SearchInfo
import com.zhongyong.globalsearchtool.utils.AppPreferencesUtils
import com.zhongyong.globalsearchtool.utils.AppUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object AddDiyDialog {


    fun showDialog(context: Context) {
        val binding: DialogAddDiyBinding = DataBindingUtil.bind(
            LayoutInflater.from(context).inflate(R.layout.dialog_add_diy, null, false)
        )!!
        val searchInfo = SearchInfo()
        binding.info = searchInfo
        val alertDialog = AlertDialog.Builder(context).setView(binding.root).show()
        binding.diyAddClick.setOnClickListener({
            // 点击添加
            GlobalScope.launch(Dispatchers.IO) {
                searchInfo.diy = 1
                DbManager.addDiyData(searchInfo)
            }.invokeOnCompletion {
                GlobalScope.launch(Dispatchers.Main) {
                    alertDialog.dismiss()
                }
            }

        })
        val browsNmaes:ArrayList<String> = ArrayList();
        var infos:ArrayList<SearchInfo> = ArrayList();

        GlobalScope.launch(Dispatchers.IO) {
             infos = AppUtils.getAllBrows(context) as ArrayList<SearchInfo>;

            for (info in infos){
                browsNmaes.add(info.name)
            }
        }.invokeOnCompletion {
            GlobalScope.launch(Dispatchers.Main) {
                val spinnerAdapter = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,browsNmaes)
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.diyAppSpinner.adapter = spinnerAdapter
            }
        }

        binding.diyAppSpinner.setOnItemSelectedListener(object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                // An item was selected. You can retrieve the selected item using
               val infoTmp = infos.get(pos);
                searchInfo?.let {
                    it.webClass = infoTmp.webClass
                    it.packageId = infoTmp.packageId
                    it.type = "web"
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        });


    }

}