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
import kotlinx.android.synthetic.main.rcm_faskes.view.*

class RecomendFaskesAdapter: RecyclerView.Adapter<RecomendFaskesAdapter.ViewHolder>() {
    private var hospitals = emptyList<HospitalsResponse>()

    fun setHospital(hospital: List<HospitalsResponse>) {
        hospitals = hospital
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val preference: UserPreference = UserPreference(view.context)

        fun bind(item: HospitalsResponse) {
            with(itemView) {
                tv_hospital.text = item.name
                tv_hospital_distance.text = "15 km"

                setOnClickListener {
                    preference.setSelectedHospital(true)

                    val intent = Intent(itemView.context, ChatFieldActivity::class.java)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rcm_faskes, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(hospitals[position])

    override fun getItemCount(): Int = hospitals.size
}