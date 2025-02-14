package com.g_one_nursesapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.g_one_nursesapp.ChatFieldActivity
import com.g_one_nursesapp.R
import com.g_one_nursesapp.api.response.HospitalsResponse
import com.g_one_nursesapp.preference.UserPreference
import com.google.gson.Gson
import kotlinx.android.synthetic.main.rcm_faskes.view.*

class RecomendFaskesAdapter: RecyclerView.Adapter<RecomendFaskesAdapter.ViewHolder>() {
    private var hospitals = emptyList<HospitalsResponse>()

    fun setHospitals(h: List<HospitalsResponse>) {
        hospitals = h
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: HospitalsResponse) {
            val gson = Gson()
            with(itemView) {
                tv_hospital.text = item.name
                tv_hospital_class.text = item.category.toString()
                tv_hospital_distance.text = "${String.format("%.1f", item.distancesFromAmbulance).toDouble()} km"

                setOnClickListener {
                    val intent = Intent(itemView.context, ChatFieldActivity::class.java)
                    intent.putExtra(ChatFieldActivity.IS_HOSPITAL_SELECTED, true)
                    intent.putExtra(ChatFieldActivity.SELECTED_HOSPITAL, gson.toJson(item))
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.rcm_faskes,
            parent,
            false
    ))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(hospitals[position])

    override fun getItemCount(): Int = hospitals.size
}