package com.nepplus.finalproject_ljh.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.nepplus.finalproject_ljh.R
import com.nepplus.finalproject_ljh.datas.AppointmentData

class AppointmentRecyclerAdapter(val mContext: Context, val mList: List<AppointmentData>) : RecyclerView.Adapter<AppointmentRecyclerAdapter.AppointmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.appointment_list_item, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {



    }

    override fun getItemCount(): Int = mList.size

    class AppointmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val titleTxt = view.findViewById<TextureView>(R.id.titleTxt)
        val dateTimeTxt = view.findViewById<TextureView>(R.id.dateTimeTxt)
        val placeNameTxt = view.findViewById<TextureView>(R.id.placeNameTxt)
        val viewPlaceMapBtn = view.findViewById<ImageView>(R.id.viewPlaceMapBtn)

    }

}