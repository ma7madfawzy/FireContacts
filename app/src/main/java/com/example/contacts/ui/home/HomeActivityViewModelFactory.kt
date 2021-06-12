package com.example.contacts.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contacts.data.data_sources.GetContactsDataSource
import com.example.contacts.data.repositories.GetContactsRepository

/**
 * ViewModel provider factory to instantiate AddContactViewModel.
 * Required given AddContactViewModel has a non-empty constructor
 */
class HomeActivityViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeActivityViewModel::class.java)) {
            return HomeActivityViewModel(
                repository = GetContactsRepository(dataSource = GetContactsDataSource())
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}