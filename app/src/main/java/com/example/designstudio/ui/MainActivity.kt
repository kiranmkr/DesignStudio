package com.example.designstudio.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.example.designstudio.R
import com.example.designstudio.databinding.ActivityMainBinding
import com.example.designstudio.util.Utils

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private var workerHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        updateUi()
        updateUiClick()

    }

    private fun updateUi() {
        homeSelection()
    }

    private fun homeSelection() {
        mainBinding.imHome.isSelected = true
        mainBinding.tvHome.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.colorAccent
            )
        )

        mainBinding.imSetting.isSelected = false
        mainBinding.tvSetting.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.grayColor
            )
        )
    }

    private fun settingSelection() {
        mainBinding.imHome.isSelected = false
        mainBinding.tvHome.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.grayColor
            )
        )

        mainBinding.imSetting.isSelected = true
        mainBinding.tvSetting.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.colorAccent
            )
        )
    }

    private fun updateUiClick() {

        mainBinding.goPro.setOnClickListener {
            Utils.showToast(this, "calling Go To Pro")
        }

        mainBinding.btnHome.setOnClickListener {
            homeSelection()
        }

        mainBinding.btnCustom.setOnClickListener {
            Utils.showToast(this, "calling Go To Custom")
        }

        mainBinding.btnSetting.setOnClickListener {
           settingSelection()
        }
    }
}