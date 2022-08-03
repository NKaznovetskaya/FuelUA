package com.zeira.fuelua.core.presentation

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zeira.fuelua.R

abstract class BaseBottomSheetFragment<B : ViewBinding> : BottomSheetDialogFragment() {

    abstract val bindingInflater: ((LayoutInflater, ViewGroup?, Boolean) -> (B))
    private var _binding: B? = null
    val binding: B
        get() {
            if (_binding == null) {
                throw NullPointerException("Binding not found!!!")
            }
            return _binding!!
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return _binding?.root
    }

    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onStart() {
        super.onStart()

        dialog?.let {
            val bottomSheet =
                it.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            val behavior = BottomSheetBehavior.from(bottomSheet)

            val height = requireContext().resources.displayMetrics.heightPixels
            Log.i("height", height.toString())

            behavior.maxHeight = (height * 0.8).toInt()

            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
}