package com.example.designstudio.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import android.widget.Toast
import com.example.designstudio.R
import java.util.ArrayList

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

    var mainCategory: String = "shapes"
    var labelNumber: Int = 1
    var labelCategory: String = "abstract"

    val request_read_permission = 2020220

    var feedBackDetails: String = "Report a Bug"
    var feedbackEmail: String = "mahipari6500@gmail.com"
    var policyLink: String = "https://designstudioapp.blogspot.com/p/privacy-policy.html"
    var termsCondition: String = "https://designstudioapp.blogspot.com/p/terms-conditions.html"

    var moreAppLink: String = "https://play.google.com/store/apps/developer?id=Label+Maker"

    val inAppKeyArray: ArrayList<String> =
        arrayListOf("life_time")

    val subscriptionsKeyArray: ArrayList<String> =
        arrayListOf("weekly_plan", "monthly_plan", "yearly_plan")

    const val inAppPurchasedkey: String = "life_time"

    const val inAppWeekly = "weekly_plan"

    const val inAppMonthly = "monthly_plan"

    const val inAppYearly = "yearly_plan"

    var colorArray = arrayOf(
        R.color.black,
        R.color.red_100,
        R.color.red_300, R.color.red_500, R.color.red_700, R.color.blue_100,
        R.color.blue_300, R.color.blue_500, R.color.blue_700, R.color.green_100, R.color.green_300,
        R.color.green_500, R.color.green_700
    )


}