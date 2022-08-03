package com.zeira.fuelua.utils

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView

class CustomGridView(context: Context, attr: AttributeSet?): GridView(context, attr) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightSpec: Int = if (layoutParams.height == LayoutParams.WRAP_CONTENT) {
            MeasureSpec.makeMeasureSpec(
                Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST
            )
        } else {
            heightMeasureSpec
        }

        super.onMeasure(widthMeasureSpec, heightSpec)
    }
}