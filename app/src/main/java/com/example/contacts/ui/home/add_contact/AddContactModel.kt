package com.example.contacts.ui.home.add_contact

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.contacts.BR

/**
 * Data validation state of the add contact form.
 */

class AddContactModel : BaseObservable() {

    var requestError: String? = null

    @get:Bindable
    var contactNameError: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.contactNameError)
        }

    @get:Bindable
    var phoneError: Int? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.phoneError)
        }

    @get:Bindable
    var loading: Boolean? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    @get:Bindable
    var contact: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.contact)
        }

    @get:Bindable
    var phone: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.phone)
        }

}