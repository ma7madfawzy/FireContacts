package com.example.contacts.ui.home.contact_details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProvider
import com.example.contacts.R
import com.example.contacts.databinding.ActivityContactDetailsBinding
import com.example.contacts.utils.ActionCallHandler
import com.example.contacts.utils.Extensions.snackError


class ContactDetailsActivity : AppCompatActivity() {

    private lateinit var callHandler: ActionCallHandler
    private lateinit var detailsViewModel: ContactDetailsViewModel
    private lateinit var binding: ActivityContactDetailsBinding

    companion object {
        const val REQUEST_CONTACT_DETAILS: Int = 125
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configViewModel()
        readExtras()
        initViews()
    }

    private fun readExtras() {
        detailsViewModel.model.dataModel = intent.getParcelableExtra("dataModel")
    }

    private fun initViews() {
        binding = ActivityContactDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewModel = detailsViewModel
        binding.position = intent.getIntExtra("adapterPosition", 0)
        initToolbar()
    }

    private fun configViewModel() {
        detailsViewModel = ViewModelProvider(this, ContactDetailsViewModelFactory())
            .get(ContactDetailsViewModel::class.java)
        observeAddContactResult()
    }

    private fun observeAddContactResult() {
        detailsViewModel.deleteContactResult
            .addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    if (detailsViewModel.deleteContactResult.get())
                        onContactDeleted()
                    else onRequestFailed()
                }
            })
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarLayout.title = detailsViewModel.model.dataModel?.contactName
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun onContactDeleted() {
        //setting result code with RESULT_OK ells the starter there was a change in data so refresh is required
        setResult(RESULT_OK)
        finish()
        Toast.makeText(
            applicationContext, getString(R.string.contact_deleted)
                .replace("*", supportActionBar?.title.toString()), Toast.LENGTH_LONG
        ).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.size > 0)
            callHandler.let { it.onRequestPermissionsResult(requestCode, grantResults) }
    }

    private fun onRequestFailed() {
        binding.container.snackError(detailsViewModel.model.requestError)
    }

    fun onCallClicked(view: View) {
        callHandler = ActionCallHandler(this, detailsViewModel.model.dataModel?.phone.toString())
            .startHandling()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
               onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        ActivityCompat.finishAfterTransition(this)
    }
}