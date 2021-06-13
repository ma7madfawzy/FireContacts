package com.example.contacts.data.repositories

import android.content.SharedPreferences
import com.example.contacts.data.data_sources.GetContactsDataSource
import com.example.contacts.data.model.Result

/**
 * Class that requests addContact on the remote data source.
 */

class GetContactsRepository(val dataSource: GetContactsDataSource) {
    suspend fun fetchContact(): Result<Any> {
        return dataSource.fetchContact()
    }
}