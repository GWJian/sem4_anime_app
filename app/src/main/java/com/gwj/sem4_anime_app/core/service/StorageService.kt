package com.gwj.sem4_anime_app.core.service

import android.net.Uri

//class StorageService(
//    private val storage:StorageReference = FirebaseStorage.getInstance().reference
//) {
//    suspend fun addImage(name:String,uri:Uri){
//        storage.child(name).putFile(uri).await()
//    }
//
//    suspend fun getImage(name: String):Uri? {
//        return try {
//            storage.child(name).downloadUrl.await()
//        } catch (e:Exception){
//            e.printStackTrace()
//            null
//        }
//    }
//}