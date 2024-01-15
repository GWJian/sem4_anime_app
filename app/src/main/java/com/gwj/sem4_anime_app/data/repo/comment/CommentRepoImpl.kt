package com.gwj.sem4_anime_app.data.repo.comment


import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.gwj.sem4_anime_app.core.services.AuthService
import com.gwj.sem4_anime_app.data.model.Comment
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class CommentRepoImpl(
    private val authService: AuthService,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
): com.gwj.sem4_anime_app.data.repo.comment.CommentRepo {
    private fun dbRefGet(): CollectionReference {
//        val firebaseUser = authService.getCurrentUser()
//        return firebaseUser?.uid?.let {
//            db.collection("comment_collection/$it/comments")
//        } ?: throw  Exception("Not Authenticated")

        return db.collection("comments")
    }

    private fun uidGet(): String{
        val firebaseUser = authService.getCurrentUser()
//        return firebaseUser?.uid ?: "randomuserchangeitlater"
        return firebaseUser?.uid ?: throw Exception("Unauthorized user")
    }
    override suspend fun getAllComments(animeId: Int): List<Comment> {
        Log.d("testcomment", animeId.toString())
        val res = dbRefGet().whereEqualTo("animeId", animeId.toString()).get().await()
        Log.d("testcomment", res.documents.toString())
        val comments = mutableListOf<Comment>()

        res?.documents?.let { docs ->
            for (doc in docs) {
                Log.d("testcomment", doc.data.toString())
                doc.data?.let {
                    it["id"] = doc.id
                    comments.add(Comment.fromHashMap(it))
                }
            }
        }
        return comments



//        val listener = dbRefGet().addSnapshotListener{ value,error ->
//            if (error != null) {
//                throw error
//            }
//
//            val comment = mutableListOf<Comment>()
//
//            value?.documents?.let { docs ->
//                for (doc in docs) {
//                    doc.data?.let {
//                        it["id"] = doc.id
//                        comment.add(Comment.fromHashMap(it))
//                    }
//                }
//                trySend(comment)
//            }
//
//        }
//        awaitClose {
//            listener.remove()
//
//        }
    }


    override suspend fun postComment(comment: Comment) {
        Log.d("commentId", comment.copy().toHashMap().toString())
        dbRefGet().add(
            comment.copy(addedBy = uidGet()).toHashMap()
        ).await()
    }

    override suspend fun getComment(id: String): Comment? {
         val querySnapshot = dbRefGet().whereEqualTo("id",id).get().await()

        return if (!querySnapshot.isEmpty) {
            val doc = querySnapshot.documents[0]

            doc.data?.let {
                it["id"] = doc.id
                Comment.fromHashMap(it)
            }
        } else {
            null
        }
    }

    override suspend fun editComment(id: String, comment: Comment) {
//        db.document(id).set(comment.toHashMap()).await()
        dbRefGet().document(id).set(comment.toHashMap()).await()
    }

    override suspend fun delete(id: String) {
        dbRefGet().document(id).delete().await()
    }
}