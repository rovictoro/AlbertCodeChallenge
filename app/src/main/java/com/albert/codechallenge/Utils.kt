package com.albert.codechallenge

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
import android.view.inputmethod.InputMethodManager
import java.lang.ref.WeakReference

object Utils {

    fun hideSoftKeyboard(activityIn: Activity) {
        val mReference = WeakReference(activityIn)
        val activity = mReference.get()
        val mHandler = Handler(Looper.getMainLooper())
        mHandler.postDelayed({
            if (activity != null) {
                val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                if (inputMethodManager != null && activity.currentFocus != null) {
                    inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)//0  InputMethodManager.RESULT_HIDDEN
                }
                activity.window.setSoftInputMode(SOFT_INPUT_ADJUST_PAN) //SOFT_INPUT_ADJUST_PAN  SOFT_INPUT_ADJUST_RESIZE
            }
        }, 100)
    }

}