package com.example.contacts.ui.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.data.model.ContactDM
import com.example.contacts.databinding.ContactRowBinding
import com.example.contacts.ui.home.contact_details.ContactDetailsActivity
import com.example.contacts.ui.home.contact_details.ContactDetailsActivity.Companion.REQUEST_CONTACT_DETAILS
import com.example.contacts.utils.ActionCallHandler

open class ContactsAdapter(
    protected var activity: Activity,
    private var dataList: List<ContactDM>
) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    protected lateinit var callHandler: ActionCallHandler
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ContactRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position], position)
    }

    fun setDataList(data: List<ContactDM>) {
        dataList = data
        notifyDataSetChanged()
    }

    fun clearData() {
        setDataList(ArrayList())
    }

    override fun getItemCount(): Int = dataList.size

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        callHandler.let { it.onRequestPermissionsResult(requestCode, grantResults) }
    }

    inner class ViewHolder(private val itemBinding: ContactRowBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            configClickListeners()
        }

        private fun configClickListeners() {
            itemBinding.callIv.setOnClickListener { v ->
                callHandler = ActionCallHandler(activity, dataList.get(adapterPosition).phone!!)
                    .startHandling()
            }
            itemBinding.root.setOnClickListener { v ->
                startContactDetailsActivity(activity,v,dataList.get(adapterPosition),adapterPosition)
            }
        }
        private fun startContactDetailsActivity(activity: Activity, view: View, contactDm: ContactDM, adapterPosition: Int) {
            val intent = Intent(activity, ContactDetailsActivity::class.java)
            intent.putExtra("dataModel", contactDm).putExtra("adapterPosition", adapterPosition)
            //config shared elements animation
            val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, view, activity.getString(R.string.transition)
            )
            ActivityCompat.startActivityForResult(activity, intent, REQUEST_CONTACT_DETAILS,
                options.toBundle()
            )
    }
        fun bind(dataModel: ContactDM, position: Int) {
            itemBinding.dataModel = dataModel
            itemBinding.position = position
        }
    }
}