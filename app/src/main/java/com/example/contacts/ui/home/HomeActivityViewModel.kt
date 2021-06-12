package com.example.contacts.ui.home

import android.content.SharedPreferences
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.data.model.ContactDM
import com.example.contacts.data.model.Result
import com.example.contacts.data.repositories.GetContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivityViewModel(private val repository: GetContactsRepository) : ViewModel() {

    val model = HomeActivityModel()
    val contactData = MediatorLiveData<List<ContactDM>>()


    fun fetchContacts() {
        // can be launched in a separate asynchronous job
        model.loading = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.fetchContact()
            if (result is Result.Success)
                contactData.postValue(result.data as ArrayList<ContactDM>)
            model.loading = false
        }
    }

    fun onThemeSelected(which: Int, editor: SharedPreferences.Editor) {
        repository.onThemeSelected(which, editor)
    }

    fun getSavedTheme(preferences: SharedPreferences): Int {
        return repository.getSavedTheme(preferences)
    }
}