package com.example.contacts.ui.home.contact_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contacts.data.data_sources.DeleteContactDataSource
import com.example.contacts.data.repositories.DeleteContactRepository

/**
 * ViewModel provider factory to instantiate AddContactViewModel.
 * Required given AddContactViewModel has a non-empty constructor
 */
class ContactDetailsViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactDetailsViewModel::class.java)) {
            return ContactDetailsViewModel(
                repository = DeleteContactRepository(dataSource = DeleteContactDataSource())
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}