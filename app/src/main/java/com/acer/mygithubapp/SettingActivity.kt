package com.acer.mygithubapp

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.acer.mygithubapp.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        preferencesHelper = PreferencesHelper(this)

        when (preferencesHelper.getThemeMode()) {
            PreferencesHelper.LIGHT_MODE -> binding.radioLight.isChecked = true
            PreferencesHelper.DARK_MODE -> binding.radioDark.isChecked = true
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioLight -> {
                    preferencesHelper.setThemeMode(PreferencesHelper.LIGHT_MODE)
                    preferencesHelper.applyTheme()
                }
                R.id.radioDark -> {
                    preferencesHelper.setThemeMode(PreferencesHelper.DARK_MODE)
                    preferencesHelper.applyTheme()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}