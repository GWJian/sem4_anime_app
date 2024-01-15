package com.gwj.sem4_anime_app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gwj.sem4_anime_app.data.model.Comment
import com.gwj.sem4_anime_app.databinding.LayoutCommentBinding

class CommentAdapter(
    private var comment: List<Comment>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener: Listener? = null
    override fun onCreateViewHolder(child: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutCommentBinding.inflate(
            LayoutInflater.from(child.context),
            child,
            false
        )
        return CommentViewHolder(binding)
    }

    override fun getItemCount() = comment.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val res = comment[position]
        if(holder is CommentAdapter.CommentViewHolder) {
            holder.binded(res)
        }
    }


    fun setComments(comments: List<Comment>) {
        this.comment = comments
        notifyDataSetChanged()
    }

    inner class CommentViewHolder(
        private val binding: LayoutCommentBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun binded(comment: Comment) {
            binding.run {
                commentUser.text = comment.addedBy
                commentText.text = comment.comment
                commentAddedOn.text = comment.addedOn
            }
        }
    }

    interface Listener {
        fun onClick(comment: Comment)
        fun onDelete(comment: Comment)
    }

}