package com.example.contacts.ui.home.add_contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contacts.data.data_sources.AddContactDataSource
import com.example.contacts.data.repositories.AddContactRepository

/**
 * ViewModel provider factory to instantiate AddContactViewModel.
 * Required given AddContactViewModel has a non-empty constructor
 */
class AddContactViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddContactViewModel::class.java)) {
            return AddContactViewModel(
                    repository = AddContactRepository(dataSource = AddContactDataSource())
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}