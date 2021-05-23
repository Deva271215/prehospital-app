package com.g_one_nursesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.g_one_nursesapp.R
import com.g_one_nursesapp.entity.relation.MessageWithAttachments
import kotlinx.android.synthetic.main.chat_list.view.*

class ChatFieldAdapter: RecyclerView.Adapter<ChatFieldAdapter.ViewHolder>() {
    private var messages = emptyList<MessageWithAttachments>()

    fun setMessage(message: List<MessageWithAttachments>) {
        messages = message
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: MessageWithAttachments) {
            with(itemView) {
                title_chat.text = item.message.message
                Glide.with(itemView.context)
                    .load(item.attachments.source)
                    .into(docPicture)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int = messages.size
}