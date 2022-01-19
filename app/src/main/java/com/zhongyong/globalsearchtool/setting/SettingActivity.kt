package com.zhongyong.globalsearchtool.setting

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.zhongyong.globalsearchtool.R
import com.zhongyong.globalsearchtool.databinding.ActivitySettingBinding
import com.zhongyong.globalsearchtool.db.DbManager
import com.zhongyong.globalsearchtool.diy.DiyActivity
import com.zhongyong.globalsearchtool.utils.AppPreferencesUtils
import com.zhongyong.globalsearchtool.utils.AppUtils
import com.zhongyong.globalsearchtool.utils.LogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


/**
 *  设置页面
 */
class SettingActivity:AppCompatActivity(), AdapterView.OnItemSelectedListener {

    lateinit var settingBinding:ActivitySettingBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingBinding = DataBindingUtil.setContentView(this, com.zhongyong.globalsearchtool.R.layout.activity_setting);


        settingBinding.caseSwitch.isChecked = AppPreferencesUtils.isOpenCase()
        settingBinding.pinyinSwitch.isChecked = AppPreferencesUtils.isOpenPinyin()
        settingBinding.appUpdateSwitch.isChecked = AppPreferencesUtils.isAppAutoUpdate();

        val browsNmaes: ArrayList<String> = ArrayList();
        var position = 1;
        lifecycleScope.launch(Dispatchers.IO) {
           val category_names = resources.getStringArray(R.array.search_engine_list)

            for ((index,info) in category_names.withIndex()) {
                browsNmaes.add(AppUtils.getBrowserName(this@SettingActivity, info))
            }
            position = AppPreferencesUtils.getDefultSearchEngine()?.toInt() ?:1;
            if (position>0){
                position = position - 1;
            }
        }.invokeOnCompletion {
            lifecycleScope.launch(Dispatchers.Main) {
                val spinnerAdapter =
                    ArrayAdapter<String>(this@SettingActivity, android.R.layout.simple_spinner_item, browsNmaes)
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                settingBinding.searchEngineSpinner.adapter = spinnerAdapter
                settingBinding.searchEngineSpinner.setSelection(position,true);
            }
        }
        // 初始化spinner,添加数据
//       val spinnerAdapter = ArrayAdapter.createFromResource(
//            this,
//            R.array.search_engine_list,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            settingBinding.searchEngineSpinner.adapter = adapter
//        }

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
            progressBar.setMessage(getString(R.string.update_local_app_data_is))
            progressBar.show();
            lifecycleScope.launch(Dispatchers.IO) {
                DbManager.updateDbData()
            }.invokeOnCompletion {
                lifecycleScope.launch(Dispatchers.Main) {
                    progressBar.dismiss()
                    Toast.makeText(this@SettingActivity,getString(R.string.update_successful),Toast.LENGTH_SHORT).show()
                }

            }
        }

        settingBinding.searchEngineSpinner.onItemSelectedListener = this;
//        settingBinding.searchEngineSpinner.setSelection(spinnerAdapter.getPosition(AppPreferencesUtils.getDefultSearchEngine()),true)

        settingBinding.settingBack.setOnClickListener({
            finish();
        })
        settingBinding.addDiyClick.setOnClickListener({
            // 去自定义设置网页页面
            val intent = Intent(this,DiyActivity::class.java)
            startActivity(intent)
        })
        settingBinding.aboutClick.setOnClickListener({

        })

        if (Locale.getDefault() == Locale.CHINA || Locale.getDefault() == Locale.TAIWAN){
            settingBinding.pinyinClick.visibility = View.VISIBLE;
        }else{
            settingBinding.pinyinClick.visibility = View.GONE;
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        val name = parent.getItemAtPosition(pos) as String
        AppPreferencesUtils.setDefultSearchEngine((pos+1).toString())
        LogUtils.e("onItemSelected: "+(pos+1))
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}