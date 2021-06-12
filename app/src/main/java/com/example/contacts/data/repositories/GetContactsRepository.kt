package com.example.contacts.data.repositories

import android.content.SharedPreferences
import com.example.contacts.data.data_sources.GetContactsDataSource
import com.example.contacts.data.model.Result

/**
 * Class that requests addContact on the remote data source.
 */

class GetContactsRepository(val dataSource: GetContactsDataSource) {
    private val THEME_TAG = "Theme"
    suspend fun fetchContact(): Result<Any> {
        return dataSource.fetchContact()
    }

    fun onThemeSelected(which: Int, editor: SharedPreferences.Editor) {
        editor.putInt(THEME_TAG, which).commit()
    }

    fun getSavedTheme(preferences: SharedPreferences): Int {
        return preferences.getInt(THEME_TAG, 1)
    }

}