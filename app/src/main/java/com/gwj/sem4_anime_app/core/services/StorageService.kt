package com.gwj.sem4_anime_app.core.services

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

class StorageService(
    private val storage: StorageReference = FirebaseStorage.getInstance().reference
) {
    suspend fun imageAdd(
        imageName:String,
        uri: Uri
    ) {
        storage.child(imageName).putFile(uri).await()
    }

    suspend fun imageGet(imageName: String): Uri? {
        return try {
            storage.child(imageName).downloadUrl.await()
        } catch (e:Exception) {
            Log.d("debugging","Image cannot be retrieved")
            e.printStackTrace()
            null
        }
    }
}