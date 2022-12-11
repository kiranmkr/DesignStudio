package com.example.designstudio.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.transition.TransitionManager
import com.example.designstudio.databinding.ActivityEditingScreenBinding
import com.example.designstudio.databinding.StickerMenuBinding
import com.example.designstudio.recyclerAdapter.ColorPickerAdapter
import com.example.designstudio.util.MoveViewTouchListener
import com.example.designstudio.util.Utils
import com.example.designstudio.util.loadThumbnail
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
    private var bgLayoutRoot: ArrayList<ConstraintLayout> = ArrayList()
    private var sizeSeekBarVal: Int = 300
    private var sizeSeekBarMin: Int = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityEditingScreenBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        stickerMenuBinding = StickerMenuBinding.bind(mainBinding.menuRoot.root)

        workerHandler.post {
            updateUi()
            bottomMenuUi()
            updateUiClick()
        }
    }

    private fun updateUi() {

        val viewTreeObserver = mainBinding.parentView.viewTreeObserver

        if (viewTreeObserver!!.isAlive) {

            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {

                    mainBinding.parentView.viewTreeObserver?.removeOnGlobalLayoutListener(this)

                    val newStickerSize = (mainBinding.parentView.width/1.5).toInt()

                    val params = FrameLayout.LayoutParams(newStickerSize, newStickerSize)
                    params.gravity = Gravity.CENTER
                    mainBinding.imgMainBg.layoutParams = params

                    Log.d("myWindows","${newStickerSize}")

                    applyStickerThinks()

                }
            })
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun applyStickerThinks() {

        val path =
            "file:///android_asset/${Utils.mainCategory}/${Utils.labelCategory}/thumbnails/${Utils.labelNumber}.png"

        Log.d("myPositionPath", path)

        mainBinding.imgMainBg.loadThumbnail(path, null)

        val moveViewTouchListener = MoveViewTouchListener(this, mainBinding.imgMainBg)

        mainBinding.imgMainBg.setOnTouchListener(moveViewTouchListener)
        moveViewTouchListener.callBacks = this

        workerHandler.postDelayed({

            Log.d("myStickerW", "${mainBinding.imgMainBg.width} -- ${mainBinding.imgMainBg.height}")

            sizeSeekBarVal = (mainBinding.imgMainBg.width) * 2

            mainBinding.sizeSeekBar.max = sizeSeekBarVal
            mainBinding.sizeSeekBar.progress = mainBinding.imgMainBg.width
            mainBinding.sizeSeekBar.setOnSeekBarChangeListener(object :
                SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                    if (fromUser && progress > sizeSeekBarMin) {

                        val params = FrameLayout.LayoutParams(progress, progress)
                        params.gravity = Gravity.CENTER
                        mainBinding.imgMainBg.layoutParams = params

                        // mainBinding.imgMainBg.rotation = progress.toFloat()
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })

        }, 0)
    }

    private fun bottomMenuUi() {
        bgLayoutButtonBar.add(stickerMenuBinding.textRotation)
        bgLayoutButtonBar.add(stickerMenuBinding.textSize)
        bgLayoutButtonBar.add(stickerMenuBinding.textColor)
        bgLayoutButtonBar.add(stickerMenuBinding.textOpacity)

        bgLayoutRoot.add(mainBinding.sizeRoot)
        bgLayoutRoot.add(mainBinding.colorRoot)
        bgLayoutRoot.add(mainBinding.opacityRoot)
        bgLayoutRoot.add(mainBinding.rotationRoot)

        stickerMenuBinding.textRotation.setOnClickListener {
            alphaManager(bgLayoutButtonBar, it.id)
            visibilityManager(bgLayoutRoot,mainBinding.rotationRoot.id)
        }
        stickerMenuBinding.textSize.setOnClickListener {
            alphaManager(bgLayoutButtonBar, it.id)
            visibilityManager(bgLayoutRoot,mainBinding.sizeRoot.id)
        }
        stickerMenuBinding.textColor.setOnClickListener {
            alphaManager(bgLayoutButtonBar, it.id)
            visibilityManager(bgLayoutRoot,mainBinding.colorRoot.id)
        }
        stickerMenuBinding.textOpacity.setOnClickListener {
            alphaManager(bgLayoutButtonBar, it.id)
            visibilityManager(bgLayoutRoot,mainBinding.opacityRoot.id)
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

    private fun visibilityManager(views: ArrayList<ConstraintLayout>, view_id: Int) {
        showAnimation()
        for (i in views.indices) {
            if (views[i].id == view_id) {
                views[i].visibility = View.VISIBLE
            } else {
                views[i].visibility = View.GONE
            }
        }
    }

    private fun showAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TransitionManager.beginDelayedTransition(mainBinding.mainroot)
        }
    }

    private fun updateUiClick() {

        mainBinding.imgBack.setOnClickListener {
            finish()
        }

        mainBinding.cardNext.setOnClickListener {
            Utils.showToast(this, "calling Next btn")
        }

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