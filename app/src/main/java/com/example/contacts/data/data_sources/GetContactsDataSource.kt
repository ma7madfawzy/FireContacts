package com.example.contacts.data.data_sources

import com.example.contacts.data.model.ContactDM
import com.example.contacts.data.model.DocumentContactList
import com.example.contacts.data.model.Result
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * Class that handles Add Contact credentials.
 */
class GetContactsDataSource : BaseDataSource() {
    suspend fun fetchContact(): Result<Any> {
        try {
            lateinit var result: Result<Any>
            coroutineScope {
                launch(Dispatchers.IO) {
                    val db = Firebase.firestore
                    db.collection(COLLECTION)
                        .get()
                        .addOnSuccessListener {
                            var contactList = it.toObjects(ContactDM::class.java)
                            result = Result.Success(contactList)
                        }
                        .addOnFailureListener { e ->
                            result = Result.Error(e)
                        }.await()
                }
            }
            return result
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }
}