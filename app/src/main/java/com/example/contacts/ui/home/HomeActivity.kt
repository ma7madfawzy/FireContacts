package com.example.contacts.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts.R
import com.example.contacts.data.model.ContactDM
import com.example.contacts.databinding.ActivityHomeBinding
import com.example.contacts.ui.home.add_contact.AddContactActivity
import com.example.contacts.ui.home.add_contact.AddContactActivity.Companion.REQUEST_ADD_CONTACT
import com.example.contacts.utils.ThemeDialogHandler
import com.google.firebase.FirebaseApp


class HomeActivity : AppCompatActivity() {

    private lateinit var themeHandlerDialog: ThemeDialogHandler
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeActivityViewModel
    private val adapter: ContactsAdapter = ContactsAdapter(this, ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        initViews()
        configViewModel()
        refreshData()
    }

    private fun initViews() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        configClickListeners()
        configRecycler()
    }


    private fun configViewModel() {
        viewModel = ViewModelProvider(this, HomeActivityViewModelFactory())
            .get(HomeActivityViewModel::class.java)
        binding.viewModel = viewModel
        observeFetchContactsResult()
        initThemeHandler()
    }

    private fun refreshData() {
        adapter.clearData()
        viewModel.fetchContacts()
    }

    private fun observeFetchContactsResult() {
        viewModel.contactData.observe(this, this::updateAdapter)
    }

    private fun updateAdapter(dataList: List<ContactDM>?) {
        dataList?.let {
            adapter.setDataList(it)
            binding.recycler.scheduleLayoutAnimation()
        }
        if (dataList == null || dataList.isEmpty()) {
            viewModel.model.dataEmpty = true
            viewModel.model.messageText = getString(R.string.empty_contacts)
        }
    }

    private fun configRecycler() {
        binding.recycler.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )
        binding.recycler.adapter = adapter
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
    }

    private fun configClickListeners() {
        binding.fab.setOnClickListener {
            startActivityForResult(
                Intent(HomeActivity@ this, AddContactActivity::class.java), REQUEST_ADD_CONTACT
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //if result code equals RESULT_OK ells the starter there was a change in data so refresh is required
        if (resultCode == RESULT_OK)
            refreshData()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (grantResults.size > 0)
            adapter.onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_theme_changer -> {
                themeHandlerDialog.showThemesPickerDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initThemeHandler() {
        themeHandlerDialog = ThemeDialogHandler(this, callback = null, delegate, getMySharedPreferences())
    }

    private fun getMySharedPreferences() =
        getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
}