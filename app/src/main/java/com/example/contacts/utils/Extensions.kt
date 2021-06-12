package com.example.contacts.utils

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

object Extensions {
    /**
     * Extension function to simplify setting an afterTextChanged action to EditText components.
     */
    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
    /**
     * Extension function to simplify showing a snackBar using the View.
     */
    fun View.snackError(@StringRes res: Int) {
        snackError(this.context.getString(res))
    }

    fun View.snackError(errorString: String?) {
        errorString?.let { Snackbar.make(this, it, Snackbar.LENGTH_SHORT).show() }
    }

    infix fun Int.modulus(other: Int) = ((this % other) + other) % other
    fun View.setRoundedBackground(@ColorInt color: Int) {
        addOnLayoutChangeListener(object: View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {

                val shape = GradientDrawable()
                shape.cornerRadius = measuredHeight / 2f
                shape.setColor(color)

                background = shape

                removeOnLayoutChangeListener(this)
            }
        })
    }
}