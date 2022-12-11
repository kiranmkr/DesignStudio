package com.example.designstudio.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.designstudio.databinding.ActivityEditingScreenBinding
import com.example.designstudio.util.MoveViewTouchListener
import com.example.designstudio.util.Utils
import com.example.designstudio.util.loadThumbnail
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class EditingScreen : AppCompatActivity(), MoveViewTouchListener.EditTextCallBacks {

    private lateinit var mainBinding: ActivityEditingScreenBinding
    private var workerHandler = Handler(Looper.getMainLooper())
    private var workerThread: ExecutorService = Executors.newCachedThreadPool()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityEditingScreenBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        workerHandler.post {
            updateUiClick()
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

        val path = "file:///android_asset/${Utils.mainCategory}/${Utils.labelCategory}/thumbnails/${Utils.labelNumber}.png"

        Log.d("myPosition", path)

        mainBinding.imgMainBg.loadThumbnail(path, null)

        val moveViewTouchListener = MoveViewTouchListener(this, mainBinding.imgMainBg)

        mainBinding.imgMainBg.setOnTouchListener(moveViewTouchListener)
        moveViewTouchListener.callBacks = this

    }

    override fun showTextControls() {

    }

    override fun setCurrentText(view: View?) {

    }

}