package com.zhongyong.globalsearchtool.setting

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zhongyong.globalsearchtool.R
import com.zhongyong.globalsearchtool.databinding.ActivitySettingBinding
import com.zhongyong.globalsearchtool.utils.AppPreferencesUtils

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

        settingBinding.searchEngineSpinner.onItemSelectedListener = this;
        settingBinding.searchEngineSpinner.setSelection(spinnerAdapter.getPosition(AppPreferencesUtils.getDefultSearchEngine()),true)

        settingBinding.settingBack.setOnClickListener({
            finish();
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