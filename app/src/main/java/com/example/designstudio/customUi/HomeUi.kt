package com.example.designstudio.customUi

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.designstudio.databinding.CustomHomeUiBinding
import com.example.designstudio.recyclerAdapter.MainRecyclerAdapter
import com.example.designstudio.util.Utils

class HomeUi @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: CustomHomeUiBinding

    init {

        val mInflater =
            getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = CustomHomeUiBinding.inflate(mInflater)

        binding.also {
            addView(it.root)
        }

        binding.goPro.setOnClickListener {
            Utils.showToast(getContext(), "calling Go To Pro")
        }

    }

    fun updateMainRoot() {
        binding.mainRecycler.apply {
            setHasFixedSize(true)
            adapter = MainRecyclerAdapter()
        }
    }

}