package com.example.designstudio.ui

import android.content.Context
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
import com.example.designstudio.model.NewCategoryData
import com.example.designstudio.model.NewDataModelJson
import com.example.designstudio.recyclerAdapter.MainRecyclerAdapter
import com.example.designstudio.util.Utils
import com.google.gson.Gson
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), TemplateClickCallBack {

    private lateinit var mainBinding: ActivityMainBinding
    private var workerHandler = Handler(Looper.getMainLooper())
    private var workerThread: ExecutorService = Executors.newCachedThreadPool()
    private lateinit var homeRoot: CustomHomeUiBinding
    private var cardListView: ArrayList<CardView> = ArrayList()

    private var newAssetsList: ArrayList<NewDataModelJson> = ArrayList()
    private var categoryList: ArrayList<NewCategoryData> = ArrayList()

    private var mainListAdapter: MainRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        homeRoot = CustomHomeUiBinding.bind(mainBinding.homeRoot.root)

        workerHandler.post {
            updateUi()
            updateUiClick()
        }

    }

    private fun updateUi() {

        homeSelection()

        cardListView.add(homeRoot.cardSvg)
        cardListView.add(homeRoot.cardSticker)
        cardListView.add(homeRoot.cardMono)
        cardListView.add(homeRoot.cardWaterColor)
        cardListView.add(homeRoot.cardShape)

        mainListAdapter = MainRecyclerAdapter()

        homeRoot.mainRecycler.adapter = mainListAdapter

        readJsonData()
    }

    private fun readJsonData() {

        workerThread.execute {

            val readJsonList: String? = loadJSONFromAsset()

            if (readJsonList != null) {


                val jsonArrayAssets = JSONArray(readJsonList)
                newAssetsList.clear()

                for (i in 0 until jsonArrayAssets.length()) {
                    val dataModel = Gson().fromJson(
                        jsonArrayAssets[i].toString(),
                        NewDataModelJson::class.java
                    )
                    newAssetsList.addAll(listOf(dataModel))
                }

                updateIndexList(0)

            } else {
                Log.d("readJsonData", "data is null")
                workerHandler.post {
                    Utils.showToast(this@MainActivity, getString(R.string.something_went_wrong))
                }
            }
        }
    }

    private fun updateIndexList(position: Int) {

        var categorySelection = "shapes"

        when (position) {
            1 -> {
                categorySelection = "shapes"
            }
            2 -> {
                categorySelection = "stickers"
            }
        }

        if (newAssetsList.isNotEmpty() && newAssetsList.size > 0) {

            for (i in 0 until newAssetsList.size) {

                if (newAssetsList[i].category == categorySelection) {

                    categoryList.clear()

                    categoryList.add(
                        NewCategoryData(
                            newAssetsList[i].totalCategory[i].categoryName,
                            newAssetsList[i].totalCategory[i].size
                        )
                    )

                    workerHandler.post {
                        mainListAdapter?.updateList(categoryList)
                    }

                }
            }
        }
    }

    //*******************This method return the Json String *********************//
    private fun loadJSONFromAsset(): String? {
        val json: String = try {
            val `is`: InputStream = assets.open("new_assets.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
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
            cardSelectionForAll(cardListView, it.id)
        }
        homeRoot.cardSticker.setOnClickListener {
            cardSelectionForAll(cardListView, it.id)
        }
        homeRoot.cardMono.setOnClickListener {
            cardSelectionForAll(cardListView, it.id)
        }
        homeRoot.cardWaterColor.setOnClickListener {
            cardSelectionForAll(cardListView, it.id)
        }
        homeRoot.cardShape.setOnClickListener {
            cardSelectionForAll(cardListView, it.id)
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