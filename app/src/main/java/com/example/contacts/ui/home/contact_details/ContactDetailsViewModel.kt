package com.example.contacts.ui.home.contact_details

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.data.model.Result
import com.example.contacts.data.repositories.DeleteContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactDetailsViewModel(private val repository: DeleteContactRepository) : ViewModel() {

    val model = ContactDetailsModel()
    val deleteContactResult = ObservableBoolean()

    fun onDeleteContactClicked() {
        model.loading = true
        deleteContactRequest()
    }

    private fun deleteContactRequest() {
        viewModelScope.launch(Dispatchers.Main) {
            val result = model.dataModel?.let { repository.deleteContact(it) }
            deleteContactResult.set(result is Result.Success)
            if (result is Result.Error)
                model.requestError = result.exception.message
            model.loading = false
        }
    }

}