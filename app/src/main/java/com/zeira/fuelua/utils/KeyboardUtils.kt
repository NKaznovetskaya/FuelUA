package com.zeira.fuelua.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment

class KeyboardUtils {
        companion object{
        fun hideKeyboard(view: View?) {
            view?.let {
                val imm =
                    it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
    }

//companion object{
//    fun Fragment.hideKeyboard() {
//        view?.let { activity?.hideKeyboard(it) }
//    }
//
//    fun Activity.hideKeyboard() {
//        hideKeyboard(currentFocus ?: View(this))
//    }
//
//    fun Context.hideKeyboard(view: View) {
//        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
//    }
//
//     fun View.hideKeyboard() {
//        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(windowToken, 0)
//    }
//    }
//
//    fun hideKeyboard(activity: Activity) {
//        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//        var view = activity.currentFocus
//        if (view == null) {
//            view = View(activity)
//        }
//        imm.hideSoftInputFromWindow(view.windowToken, 0)
//    }
//
//
//    fun showKeyboard(activity: Activity) {
//        var view = activity.currentFocus
//        if (view == null) view = View(activity)
//        val context = view.context
//        val mgr = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        mgr.showSoftInput(view, InputMethodManager.SHOW_FORCED)
//    }


}