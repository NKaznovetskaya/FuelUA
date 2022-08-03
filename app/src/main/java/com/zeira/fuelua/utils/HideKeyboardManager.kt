package com.zeira.fuelua.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager

import kotlin.math.roundToInt

class HideKeyboardManager {
    @SuppressLint("ClickableViewAccessibility")
    fun initHideListener(root: View?) {
        root?.setOnTouchListener { view, event ->
            if (view != null && event != null) {
                val rect = Rect()
                if (view.getGlobalVisibleRect(rect) && checkIntersect(rect, event)) {
                    hideKeyboard(view)
                }
            }
            return@setOnTouchListener true
        }
    }

    private fun checkIntersect(rect: Rect, event: MotionEvent): Boolean {
        return try {
            (rect.intersect(
                event.x.roundToInt(),
                event.y.roundToInt(),
                event.x.roundToInt(),
                event.y.roundToInt()
            ).not())
        } catch (e: IllegalArgumentException) {
            false
        }
    }

//    private fun hideKeyboard(view: View?) {
//       KeyboardUtils.hideKeyboard(view)
//
//        view?.clearFocus()
//    }

    private fun hideKeyboard(view: View?) {
        view?.let {
            val imm =
                it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
        view?.clearFocus()
    }
}