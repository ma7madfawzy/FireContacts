package com.example.contacts.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import com.example.contacts.R


class ThemeDialogHandler(
    private val context: Context,
    private val callback: ThemePickerCallback,
    private val delegate: AppCompatDelegate
) {
    internal fun chooseThemeDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(context.getString(R.string.choose_theme_text))
        val styles = arrayOf(
            context.getString(R.string.light_theme),
            context.getString(R.string.dark_theme),
            context.getString(R.string.system_efault_theme)
        )
        val checkedItem = 0

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which -> setTheme(dialog, which) }
        val dialog = builder.create()
        dialog.show()
    }

    internal fun setTheme(dialog: DialogInterface? = null, which: Int) {
        AppCompatDelegate.setDefaultNightMode(getWhichMode(which))
        delegate.applyDayNight()
        dialog?.dismiss()
        callback.onThemeSelected(which)
    }

    private fun getWhichMode(which: Int): Int {
        return when (which) {
            0 -> AppCompatDelegate.MODE_NIGHT_NO
            1 -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
    }

    interface ThemePickerCallback {
        fun onThemeSelected(which: Int)
    }
}
