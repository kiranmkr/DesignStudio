package com.example.designstudio.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.transition.TransitionManager
import com.example.designstudio.R
import com.example.designstudio.customCallBack.TemplateClickCallBack
import com.example.designstudio.databinding.ActivityMainBinding
import com.example.designstudio.databinding.CustomHomeUiBinding
import com.example.designstudio.recyclerAdapter.MainRecyclerAdapter
import com.example.designstudio.util.Utils
import java.util.ArrayList

class MainActivity : AppCompatActivity(), TemplateClickCallBack {

    private lateinit var mainBinding: ActivityMainBinding
    private var workerHandler = Handler(Looper.getMainLooper())
    private lateinit var homeRoot: CustomHomeUiBinding
    private var cardListView: ArrayList<CardView> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        homeRoot = CustomHomeUiBinding.bind(mainBinding.homeRoot.root)

        updateUi()

        updateUiClick()

    }

    private fun updateUi() {

        homeSelection()

        cardListView.add(homeRoot.cardSvg)
        cardListView.add(homeRoot.cardSticker)
        cardListView.add(homeRoot.cardMono)
        cardListView.add(homeRoot.cardWaterColor)
        cardListView.add(homeRoot.cardShape)

        homeRoot.mainRecycler.apply {
//            setHasFixedSize(true)
            adapter = MainRecyclerAdapter()
        }
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

        homeIconClick()

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

    private fun homeIconClick() {

        homeRoot.goPro.setOnClickListener {
            Utils.showToast(this, "calling Go to Pro")
        }

        homeRoot.cardSvg.setOnClickListener {
            cardSelectionForAll(cardListView,it.id)
        }
        homeRoot.cardSticker.setOnClickListener {
            cardSelectionForAll(cardListView,it.id)
        }
        homeRoot.cardMono.setOnClickListener {
            cardSelectionForAll(cardListView,it.id)
        }
        homeRoot.cardWaterColor.setOnClickListener {
            cardSelectionForAll(cardListView,it.id)
        }
        homeRoot.cardShape.setOnClickListener {
            cardSelectionForAll(cardListView,it.id)
        }
    }

    private fun cardSelectionForAll(views: ArrayList<CardView>, view_id: Int) {

        for (i in views.indices) {

            if (views[i].id == view_id) {
                views[i].setCardBackgroundColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.colorAccent
                    )
                )
            } else {
                Log.d("myCard", "Card is disable ")
                views[i].setCardBackgroundColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.cardBack
                    )
                )
            }


        }
    }

    override fun onItemClickListener(labelStatus: Boolean) {

    }

    private fun showAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TransitionManager.beginDelayedTransition(mainBinding.mainroot)
        }
    }
}