package com.example.designstudio.recyclerAdapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.designstudio.R
import com.example.designstudio.model.NewCategoryData
import com.example.designstudio.model.NewDataModelJson
import com.example.designstudio.util.Utils
import java.util.*
import kotlin.collections.ArrayList

class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {

    var totalCategory: ArrayList<NewCategoryData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_recycler_row_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.title.text = Utils.listOfCategory[position].uppercase(Locale.ENGLISH)
        holder.title.text = totalCategory[position].categoryName.uppercase(Locale.ENGLISH)
        setCatItemRecycler(holder.recyclerItemView, Utils.listOfCategory[position])
    }

    override fun getItemCount(): Int {
        return totalCategory.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var title: TextView
        var recyclerItemView: RecyclerView

        init {
            title = itemView.findViewById(R.id.cat_title)
            recyclerItemView = itemView.findViewById(R.id.item_recycler)

        }

    }

    private fun setCatItemRecycler(rec: RecyclerView, categoryName: String) {
        rec.apply {
            setHasFixedSize(true)
            adapter = MainSubRecyclerAdapter(categoryName)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: java.util.ArrayList<NewCategoryData>) {
        totalCategory.clear()
        totalCategory.addAll(list)
        notifyDataSetChanged()

    }

}