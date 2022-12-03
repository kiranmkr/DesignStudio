package com.example.designstudio.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@SuppressLint("CustomSplashScreen")
class NewSplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        try {
            runBlocking { // this: CoroutineScope
                launch { doWork() }
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    // this is your first suspending function
    private suspend fun doWork() {
        delay(1000L)
    }

}