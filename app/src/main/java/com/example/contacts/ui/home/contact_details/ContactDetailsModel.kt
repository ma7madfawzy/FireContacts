package com.example.contacts.ui.home.contact_details

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.contacts.BR
import com.example.contacts.data.model.ContactDM

/**
 * Data validation state of the add contact form.
 */

class ContactDetailsModel : BaseObservable() {

    var requestError: String? = null

    @get:Bindable
    var loading: Boolean? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    @get:Bindable
    var dataModel: ContactDM? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.dataModel)
        }
}