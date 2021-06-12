package com.example.contacts.data.repositories

import com.example.contacts.data.data_sources.DeleteContactDataSource
import com.example.contacts.data.model.Result
import com.example.contacts.data.model.ContactDM

/**
 * Class that requests addContact on the remote data source.
 */

class DeleteContactRepository(val dataSource: DeleteContactDataSource) {

    suspend fun deleteContact(contactDM: ContactDM): Result<Unit> {
       return  dataSource.deleteContact(contactDM)
    }
}