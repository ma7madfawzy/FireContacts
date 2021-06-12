package com.example.contacts.ui.home.add_contact

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProvider
import com.example.contacts.R
import com.example.contacts.databinding.ActivityAddContactBinding
import com.example.contacts.utils.Extensions.snackError

class AddContactActivity : AppCompatActivity() {

    private lateinit var viewModel: AddContactViewModel
    private lateinit var binding: ActivityAddContactBinding
    companion object {
        const val REQUEST_ADD_CONTACT: Int = 963
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        configViewModel()
    }

    private fun initViews() {
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        configActionDone()
    }

    private fun configViewModel() {
        viewModel = ViewModelProvider(this, AddContactViewModelFactory())
            .get(AddContactViewModel::class.java)
        binding.viewModel = viewModel
        observeAddContactResult()
    }

    private fun observeAddContactResult() {
        viewModel.addContactResult
            .addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    if (viewModel.addContactResult.get())
                        onContactAdded()
                    else onRequestFailed()
                }
            })
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarLayout.title = title
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun configActionDone() {
        binding.phoneEt.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    viewModel.onAddContactClicked()
            }
            false
        }
    }

    private fun onContactAdded() {
        //setting result code with RESULT_OK ells the starter there was a change in data so refresh is required
        setResult(RESULT_OK)
        finish()
        Toast.makeText(
            applicationContext, getString(R.string.contact_added)
                .replace("*", supportActionBar?.title.toString()), Toast.LENGTH_LONG
        ).show()
    }

    private fun onRequestFailed() {
        binding.container.snackError(viewModel.model.requestError)
    }
}