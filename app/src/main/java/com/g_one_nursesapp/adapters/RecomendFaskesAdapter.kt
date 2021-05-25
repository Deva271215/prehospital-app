package com.g_one_nursesapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.g_one_nursesapp.R
import com.g_one_nursesapp.api.response.HospitalsResponse
import kotlinx.android.synthetic.main.rcm_faskes.view.*

class RecomendFaskesAdapter: RecyclerView.Adapter<RecomendFaskesAdapter.ViewHolder>() {
    private var hospitals = emptyList<HospitalsResponse>()

    fun setHospital(hospital: List<HospitalsResponse>) {
        hospitals = hospital
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: HospitalsResponse) {
            with(itemView) {
                tv_hospital.text = item.name
                tv_hospital_distance.text = "15 km"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rcm_faskes, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(hospitals[position])
    }

    override fun getItemCount(): Int = hospitals.size
}