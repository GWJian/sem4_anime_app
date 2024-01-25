package com.gwj.sem4_anime_app.data.repo.comment


import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.gwj.sem4_anime_app.core.services.AuthService
import com.gwj.sem4_anime_app.data.model.Comment
import kotlinx.coroutines.channels.awaitClose
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

   override suspend fun dbUserNameGet(): String? {
        return authService.getCurrentUser()?.uid?.let {
            val res = db.collection("users").document(it).get().await()
            res.data?.get("username") as String
        }
    }

    private fun uidGet(): String{
        val firebaseUser = authService.getCurrentUser()
//        return firebaseUser?.uid ?: "randomuserchangeitlater"
        return firebaseUser?.uid ?: throw Exception("Unauthorized user")
    }
    override suspend fun getAllComments(animeId: Int) = callbackFlow {
        Log.d("testcomment", animeId.toString())
//        val res = dbRefGet().whereEqualTo("animeId", animeId.toString()).get().await()
        val res = dbRefGet().whereEqualTo("animeId", animeId.toString()).addSnapshotListener {value, error ->
            if (error != null) {
                throw error
            }
            val comments = mutableListOf<Comment>()

            value?.documents?.let { docs ->
                for (doc in docs) {
                    doc.data?.let {
                        it["id"] = doc.id
                        comments.add(Comment.fromHashMap(it))
                    }
                }
                trySend(comments)
            }

        }
        awaitClose {
            res.remove()
        }
//        Log.d("testcomment", res.documents.toString())
//        val comments = mutableListOf<Comment>()
//
//        res?.documents?.let { docs ->
//            for (doc in docs) {
//                Log.d("testcomment", doc.data.toString())
//                doc.data?.let {
//                    it["id"] = doc.id
//                    comments.add(Comment.fromHashMap(it))
//                }
//            }
//        }
//        return comments



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


    override suspend fun postComment( comment: Comment) {
        Log.d("commentId", comment.copy().toHashMap().toString())
        dbUserNameGet()
        dbUserNameGet()?.let {
            comment.copy(addedBy = it).toHashMap()
        }?.let {
            dbRefGet().add(

                it
            ).await()
        }
    }

    override suspend fun getComment(id: String): Comment? {
         val doc = dbRefGet().document(id).get().await()
        return doc.data?.let {
            it["id"] = doc.id
            Comment.fromHashMap(it)
        }
    }

    override suspend fun editComment(id: String, comment: Comment) {
//        db.document(id).set(comment.toHashMap()).await()
        Log.d("commentId", comment.copy().toHashMap().toString())
        dbRefGet().document(id).set(comment.toHashMap()).await()
//        dbUserNameGet()?.let { comment.copy(addedBy = it).toHashMap() }?.let {
//            dbRefGet().document(id).set(
//                it
//            ).await()
//        }
    }

    override suspend fun delete(id: String) {
        Log.d("commentId", id)
        dbRefGet().document(id).delete().addOnCompleteListener {
            Log.d("commentId", "reached ${it.isSuccessful}")
        }.addOnCanceledListener {
            Log.d("commentId", "not reached")
        }
    }
}