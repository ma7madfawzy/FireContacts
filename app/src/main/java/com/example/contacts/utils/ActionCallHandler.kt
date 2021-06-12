package com.example.contacts.utils

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.example.contacts.R


class ActionCallHandler(private val activity: Activity, private val phoneNumber: String) {
    private val CALL_PHONE: String = Manifest.permission.CALL_PHONE
    private val REQUEST_CALL_PERMISSION: Int = 225

    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == REQUEST_CALL_PERMISSION)
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                onPermissionDenied()
            } else
                startHandling()
    }

    private fun onPermissionDenied() {
        showAlert(
            activity.getString(R.string.permission_denied),
            activity.getString(R.string.call_action_cancelled)
        )
    }

    fun startHandling(): ActionCallHandler {
        if (checkPermission()) {
            callAction()
        }
        return this
    }

    private fun checkPermission(): Boolean {
        //permission is automatically granted on sdk<23 upon installation
        if (Build.VERSION.SDK_INT < 23) return true
        //This checks for the permission
        if (checkSelfPermission(activity, CALL_PHONE)
            == PackageManager.PERMISSION_GRANTED
        ) return true
        else {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
                showRationaleAlert()
            else //Requesting permission
                requestPermission()
        }
        return false
    }

    private fun showRationaleAlert() {
        showAlert(activity.getString(R.string.permission_required),
            activity.getString(R.string.call_permission_clarification),
            { _, _ -> requestPermission() }
        )
    }

    private fun showAlert(
        title: String? = null, message: String? = null,
        listener: DialogInterface.OnClickListener? = null
    ) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(message)
            .setTitle(title)
        builder.setPositiveButton(activity.getString(R.string.ok), listener)
        builder.create().show()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity, arrayOf(Manifest.permission.CALL_PHONE),
            REQUEST_CALL_PERMISSION
        )
    }

    private fun callAction() {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        activity.startActivity(callIntent)
    }
}