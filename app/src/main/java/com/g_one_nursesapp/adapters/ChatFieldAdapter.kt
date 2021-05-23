package com.g_one_nursesapp.adapters

import android.util.Log
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
                time.text = item.message.time
                if (!item.message.response.isNullOrEmpty()) {
                    text_respon.visibility = View.VISIBLE
                    value_respon.visibility = View.VISIBLE
                    value_respon.text = item.message.response
                }
                if (!item.message.result.isNullOrEmpty()) {
                    text_hasil.visibility = View.VISIBLE
                    value_hasil.visibility = View.VISIBLE
                    value_hasil.text = item.message.result
                }
                if (!item.message.condition.isNullOrEmpty()) {
                    text_kondisi.visibility = View.VISIBLE
                    value_kondisi.visibility = View.VISIBLE
                    value_kondisi.text = item.message.condition
                }
                if (!item.message.action.isNullOrEmpty()) {
                    text_aksi.visibility = View.VISIBLE
                    value_aksi.visibility = View.VISIBLE
                    value_aksi.text = item.message.action
                }
                if (!item.attachments.isNullOrEmpty()) {
                    cardView.visibility = View.VISIBLE
                    Glide.with(itemView.context)
                        .load(item.attachments[0].source)
                        .into(docPicture)
                }
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