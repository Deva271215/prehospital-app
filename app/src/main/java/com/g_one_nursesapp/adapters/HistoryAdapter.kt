package com.g_one_nursesapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.g_one_nursesapp.ChatFieldActivity
import com.g_one_nursesapp.R
import com.g_one_nursesapp.api.response.ChatResponse
import kotlinx.android.synthetic.main.history_list.view.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private var chats = ArrayList<ChatResponse>()

    fun setChats(c: ArrayList<ChatResponse>) {
        chats.clear()
        chats.addAll(c)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: ChatResponse) {
            val splitDate = item.createdAt.split(".").toTypedArray()
            val date = LocalDateTime.parse(splitDate[0])
            val formatter = DateTimeFormatter.ofPattern("dd LLLL YYYY")
            with(itemView) {
                traumaName.text = item.prediction
                historyTime.text = formatter.format(date)
                historyName.text = item.hospital.name
                setOnClickListener {
                    val intent = Intent(itemView.context, ChatFieldActivity::class.java)
                    intent.putExtra(ChatFieldActivity.CHAT_ROOM_ID, item.id)
                    intent.putExtra(ChatFieldActivity.IS_FROM_HISTORY, true)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = HistoryAdapter.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.history_list, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(chats[position])

    override fun getItemCount(): Int = chats.size
}