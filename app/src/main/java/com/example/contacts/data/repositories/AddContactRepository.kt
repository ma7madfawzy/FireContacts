package com.example.contacts.data.repositories

import com.example.contacts.data.data_sources.AddContactDataSource
import com.example.contacts.data.model.Result
import com.example.contacts.data.model.ContactDM

/**
 * Class that requests addContact on the remote data source.
 */

class AddContactRepository(val dataSource: AddContactDataSource) {

    suspend fun addContact(contactDM: ContactDM): Result<Unit> {
       return  dataSource.addContact(contactDM)
    }
}