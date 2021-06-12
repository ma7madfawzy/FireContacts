package com.example.contacts.ui.home.add_contact

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.R
import com.example.contacts.data.model.ContactDM
import com.example.contacts.data.model.Result
import com.example.contacts.data.repositories.AddContactRepository
import com.example.contacts.utils.ValidationUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddContactViewModel(private val repository: AddContactRepository) : ViewModel() {

    val model = AddContactModel()
    val addContactResult = ObservableBoolean()

    fun onAddContactClicked() {
        if (validateData()) {
            model.loading = true
            addContactRequest()
        }
    }

    private fun addContactRequest() {
        val contactDm = ContactDM(
            model.contact.toString(),
            model.phone.toString()
        )

        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.addContact(contactDm)
            addContactResult.set(result is Result.Success)
            if (result is Result.Error)
                model.requestError = result.exception.message
            model.loading = false
        }
    }

    private fun validateData(): Boolean {
        var valid = true
        if (!ValidationUtils.isPhoneValid(model.phone)) {
            model.phoneError = R.string.invalid_phone
            valid = false
        }
        if (!ValidationUtils.isFullNameValid(model.contact)) {
            model.contactNameError = R.string.invalid_name
            valid = false
        }
        return valid
    }


}