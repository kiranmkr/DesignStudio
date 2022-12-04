package com.example.designstudio.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import android.widget.Toast
import java.util.HashMap

object Utils {

    val readPermissionPass = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        arrayOf(
            Manifest.permission.ACCESS_MEDIA_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    } else {
        arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    fun showToast(c: Context, message: String) {
        try {
            if (!(c as Activity).isFinishing) {
                c.runOnUiThread { //show your Toast here..
                    Toast.makeText(c.applicationContext, message, Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    var listOfCategory = arrayOf(
        "events",
        "baby shower",
        "music",
        "soap",
        "camera",
        "candy",
        "kids",
        "drinks",
        "cosmetic",
        "car",
        "hazard",
        "laptop",
        "ramzan",
        "shipping"
    )

    @JvmStatic
    val categoryMap: HashMap<String, Int> =
        hashMapOf(
            "candy" to 10,
            "car" to 10,
            "cosmetic" to 10,
            "drinks" to 10,
            "hazard" to 10,
            "kids" to 10,
            "laptop" to 10,
            "ramzan" to 10,
            "shipping" to 10,
            "baby shower" to 20,
            "camera" to 10,
            "events" to 20,
            "music" to 11,
            "soap" to 20,
        )

}