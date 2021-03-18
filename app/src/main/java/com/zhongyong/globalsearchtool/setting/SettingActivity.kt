package com.zhongyong.globalsearchtool.setting

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.zhongyong.globalsearchtool.R
import com.zhongyong.globalsearchtool.databinding.ActivitySettingBinding
import com.zhongyong.globalsearchtool.db.DbManager
import com.zhongyong.globalsearchtool.diy.DiyActivity
import com.zhongyong.globalsearchtool.utils.AppPreferencesUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 *  设置页面
 */
class SettingActivity:AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var settingBinding:ActivitySettingBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingBinding = DataBindingUtil.setContentView(this, R.layout.activity_setting);


        settingBinding.caseSwitch.isChecked = AppPreferencesUtils.isOpenCase()
        settingBinding.pinyinSwitch.isChecked = AppPreferencesUtils.isOpenPinyin()
        settingBinding.appUpdateSwitch.isChecked = AppPreferencesUtils.isAppAutoUpdate();

        // 初始化spinner,添加数据
       val spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.search_engine_list,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            settingBinding.searchEngineSpinner.adapter = adapter
        }

        settingBinding.caseClick.setOnClickListener({
            if (AppPreferencesUtils.isOpenCase()){
                AppPreferencesUtils.setOpenCase(false)
            }else{
                AppPreferencesUtils.setOpenCase(true)
            }
            settingBinding.caseSwitch.isChecked = AppPreferencesUtils.isOpenCase()
        })

        settingBinding.pinyinClick.setOnClickListener({
            if (AppPreferencesUtils.isOpenPinyin()){
                AppPreferencesUtils.setOpenPinyin(false)
            }else{
                AppPreferencesUtils.setOpenPinyin(true)
            }
            settingBinding.pinyinSwitch.isChecked = AppPreferencesUtils.isOpenPinyin()
        })

        settingBinding.appUpdateOpenClick.setOnClickListener({
            if (AppPreferencesUtils.isAppAutoUpdate()){
                AppPreferencesUtils.setAppAutoUpdate(false)
            }else{
                AppPreferencesUtils.setAppAutoUpdate(true)
            }
            settingBinding.appUpdateSwitch.isChecked = AppPreferencesUtils.isAppAutoUpdate();
        })

        settingBinding.updateAppClick.setOnClickListener {
            val progressBar = ProgressDialog(this);
            progressBar.setMessage("正在更新本地APP数据")
            progressBar.show();
            lifecycleScope.launch(Dispatchers.IO) {
                DbManager.updateDbData()
            }.invokeOnCompletion {
                lifecycleScope.launch(Dispatchers.Main) {
                    progressBar.dismiss()
                    Toast.makeText(this@SettingActivity,"更新成功！",Toast.LENGTH_SHORT).show()
                }

            }
        }

        settingBinding.searchEngineSpinner.onItemSelectedListener = this;
        settingBinding.searchEngineSpinner.setSelection(spinnerAdapter.getPosition(AppPreferencesUtils.getDefultSearchEngine()),true)

        settingBinding.settingBack.setOnClickListener({
            finish();
        })
        settingBinding.addDiyClick.setOnClickListener({
            // 去自定义设置网页页面
            val intent = Intent(this,DiyActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        val name = parent.getItemAtPosition(pos) as String
        AppPreferencesUtils.setDefultSearchEngine(name)
        Log.e("wbb", "onItemSelected: "+name)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}