package com.example.contacts.ui.home

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.contacts.BR

/**
 * Data validation state of the add contact form.
 */

class HomeActivityModel : BaseObservable() {
    @get:Bindable
    var messageText: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.messageText)
        }

    @get:Bindable
    var loading: Boolean? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    @get:Bindable
    var dataEmpty: Boolean? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.dataEmpty)
        }

}