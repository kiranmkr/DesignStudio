package com.example.designstudio.recyclerAdapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.designstudio.R
import com.example.designstudio.billing.GBilling
import com.example.designstudio.customCallBack.TemplateClickCallBack
import com.example.designstudio.ui.MainActivity
import com.example.designstudio.util.Utils
import com.example.designstudio.util.loadThumbnail

class MainSubRecyclerAdapter(totalCount: Int, categoryName: String) :
    RecyclerView.Adapter<MainSubRecyclerAdapter.ViewHolder>() {

    private var mContext: Activity? = null
    private var category: String = categoryName
    private var totalNumber: Int = totalCount
    private var callBackMain: TemplateClickCallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        mContext = parent.context as Activity

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.template_trending_cat_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position > 2) {
            if (GBilling.isSubscribedOrPurchasedSaved) {
                holder.adsFreeIcon.visibility = View.GONE
            } else {
                holder.adsFreeIcon.visibility = View.VISIBLE
            }
        } else {
            holder.adsFreeIcon.visibility = View.GONE
        }

        val path: String =
            "file:///android_asset/${Utils.mainCategory}/${
                category
            }/thumbnails/" + (position + 1) + ".png"

        Log.d("myPosition", path)

        holder.placeHolder.loadThumbnail(path, null)

    }

    override fun getItemCount(): Int {
        return totalNumber
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var placeHolder: ImageView
        var adsFreeIcon: ImageView

        init {

            callBackMain = mContext as MainActivity

            placeHolder = itemView.findViewById(R.id.placeHolder)
            adsFreeIcon = itemView.findViewById(R.id.adsFreeIcon)

            itemView.setOnClickListener {

                Utils.fileLabelNumber = adapterPosition + 1
                Utils.subCategory = category

                if (adsFreeIcon.visibility == View.VISIBLE) {
                    callBackMain?.onItemClickListener(
                        true
                    )
                } else {
                    callBackMain?.onItemClickListener(
                        false
                    )
                }

            }

        }

    }

}