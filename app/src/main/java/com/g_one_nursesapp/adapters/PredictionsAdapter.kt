package com.g_one_nursesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.g_one_nursesapp.R
import com.g_one_nursesapp.api.response.PredictionResponse
import com.g_one_nursesapp.entity.MessageEntity
import kotlinx.android.synthetic.main.predictions_list.view.*

class PredictionsAdapter: RecyclerView.Adapter<PredictionsAdapter.ViewHolder>() {
    private var predictions = ArrayList<PredictionResponse>()

    fun setPredictions(p: MutableList<PredictionResponse>) {
        predictions.clear()
        predictions.addAll(p)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: PredictionResponse) {
            val value = String.format("%.1f", item.value).toDouble()
            with(itemView) {
                traumaName.text = item.label
                traumaPercent.text = "${value*100}%"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return PredictionsAdapter.ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.predictions_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(predictions[position])

    override fun getItemCount(): Int = predictions.size
}