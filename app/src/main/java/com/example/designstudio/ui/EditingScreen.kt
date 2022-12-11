package com.example.designstudio.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.designstudio.R
import com.example.designstudio.databinding.ActivityEditingScreenBinding
import com.example.designstudio.databinding.StickerMenuBinding
import com.example.designstudio.recyclerAdapter.ColorPickerAdapter
import com.example.designstudio.util.MoveViewTouchListener
import com.example.designstudio.util.Utils
import com.example.designstudio.util.loadThumbnail
import java.util.ArrayList
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EditingScreen : AppCompatActivity(), MoveViewTouchListener.EditTextCallBacks,
    ColorPickerAdapter.ColorPickerClick {

    private lateinit var mainBinding: ActivityEditingScreenBinding
    private lateinit var stickerMenuBinding: StickerMenuBinding

    private var workerHandler = Handler(Looper.getMainLooper())
    private var workerThread: ExecutorService = Executors.newCachedThreadPool()

    private var colorPickerAdapter = ColorPickerAdapter(this)

    private var bgLayoutButtonBar: ArrayList<ConstraintLayout> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityEditingScreenBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        stickerMenuBinding = StickerMenuBinding.bind(mainBinding.menuRoot.root)

        workerHandler.post {
            bottomMenuUi()
            updateUiClick()
        }
    }

    private fun bottomMenuUi() {
        bgLayoutButtonBar.add(stickerMenuBinding.textRotation)
        bgLayoutButtonBar.add(stickerMenuBinding.textSize)
        bgLayoutButtonBar.add(stickerMenuBinding.textColor)
        bgLayoutButtonBar.add(stickerMenuBinding.textOpacity)

        stickerMenuBinding.textRotation.setOnClickListener {
            alphaManager(bgLayoutButtonBar, it.id)
        }
        stickerMenuBinding.textSize.setOnClickListener {
            alphaManager(bgLayoutButtonBar, it.id)
        }
        stickerMenuBinding.textColor.setOnClickListener {
            alphaManager(bgLayoutButtonBar, it.id)
        }
        stickerMenuBinding.textOpacity.setOnClickListener {
            alphaManager(bgLayoutButtonBar, it.id)
        }
    }


    private fun alphaManager(views: ArrayList<ConstraintLayout>, view_id: Int) {
        for (i in views.indices) {
            if (views[i].id == view_id) {
                views[i].alpha = 1.0f
            } else {
                views[i].alpha = 0.4f
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun updateUiClick() {

        mainBinding.imgBack.setOnClickListener {
            finish()
        }

        mainBinding.cardNext.setOnClickListener {
            Utils.showToast(this, "calling Next btn")
        }

        val path =
            "file:///android_asset/${Utils.mainCategory}/${Utils.labelCategory}/thumbnails/${Utils.labelNumber}.png"

        Log.d("myPositionPath", path)

        mainBinding.imgMainBg.loadThumbnail(path, null)

        val moveViewTouchListener = MoveViewTouchListener(this, mainBinding.imgMainBg)

        mainBinding.imgMainBg.setOnTouchListener(moveViewTouchListener)
        moveViewTouchListener.callBacks = this

        Log.d("myRotation", "${mainBinding.imgMainBg.rotation}")

        mainBinding.rotationSeekBar.max = 360
        mainBinding.rotationSeekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mainBinding.imgMainBg.rotation = progress.toFloat()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        //SeekAlpha Code
        mainBinding.seekBarOpacity.max = 10
        mainBinding.seekBarOpacity.progress = 10
        mainBinding.seekBarOpacity.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        if (progress == 10) {
                            mainBinding.imgMainBg.alpha = 1.0f
                        } else {
                            mainBinding.imgMainBg.alpha = "0.$progress".toFloat()
                        }
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })

        mainBinding.reGgColor.setHasFixedSize(true)
        mainBinding.reGgColor.adapter = colorPickerAdapter

    }

    override fun showTextControls() {

    }

    override fun setCurrentText(view: View?) {

    }

    override fun setOnColorClickListener(position: Int) {

        Log.d("myColorPosition", "$position")

        mainBinding.imgMainBg.setColorFilter(
            ContextCompat.getColor(
                this,
                Utils.colorArray[position]
            )
        )

    }

}