package com.example.contacts.utils

import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import com.example.contacts.utils.Extensions.afterTextChanged
import com.example.contacts.utils.Extensions.modulus
import com.example.contacts.utils.Extensions.setRoundedBackground
import com.example.contacts.utils.Extensions.snackError
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.textfield.TextInputLayout


object BindingUtil {
    @BindingAdapter("error")
    @JvmStatic
    fun error(view: View, error: Int) {
        if (error == 0) return
        when (view) {
            is EditText -> view.error =
                view.getContext().getString(error)
            is TextInputLayout -> {
                error(view, error)
            }
            else -> view.snackError(error)
        }
    }

    @BindingAdapter("error")
    @JvmStatic
     fun error(view: TextInputLayout, error: Int) {
        if (error == 0) return
        view.isErrorEnabled = true
        view.error = view.context.getString(error)
        if (view.tag != null && view.tag is TextWatcher) return  //watcher already set
        if (view.editText != null) view.editText!!.afterTextChanged {
            view.error = null
            view.isErrorEnabled = false
            view.tag = this
        }
    }


    @BindingAdapter("snack_error")
    @JvmStatic
    fun snackError(view: View, @StringRes error: Int) {
        if (error != 0)
            view.snackError(error)
    }

    @BindingAdapter("snack_error")
    @JvmStatic
    fun snackError(view: View, error: String?) {
        error?.let { view.snackError(error) }
    }

    @BindingAdapter("firstChar")
    @JvmStatic
    fun firstChar(view: TextView, text: String?) {
        text?.let {
            view.text = text.toCharArray()[0].uppercase()
        }
    }

    @BindingAdapter("random_color_background")
    @JvmStatic
    fun random_color_background(view: View, position: Int?) {
        //get position based color index
        val rainbow = view.context.resources.getIntArray(com.example.contacts.R.array.rainbow)
        //@Int.modulus is an extension function located in Extensions object in utils help with the modulus operation
        val index = position?.modulus(rainbow.size)
        view.setRoundedBackground(rainbow[index!!])
    }

    @BindingAdapter("enableCollapsing")
    @JvmStatic
    fun enableCollapsing(collapsingToolbarLayout: CollapsingToolbarLayout, enabled: Boolean?) {
        enabled?.let {
            val lp = collapsingToolbarLayout.layoutParams as AppBarLayout.LayoutParams
            if (it) {
                lp.scrollFlags =
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
            } else {
                lp.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
            }
            collapsingToolbarLayout.layoutParams = lp
        }
    }
}