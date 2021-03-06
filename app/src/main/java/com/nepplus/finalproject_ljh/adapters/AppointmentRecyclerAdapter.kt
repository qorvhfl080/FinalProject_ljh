package com.nepplus.finalproject_ljh.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nepplus.finalproject_ljh.R
import com.nepplus.finalproject_ljh.ViewAppointmentDetailActivity
import com.nepplus.finalproject_ljh.ViewMapActivity
import com.nepplus.finalproject_ljh.datas.AppointmentData
import java.text.SimpleDateFormat

class AppointmentRecyclerAdapter(val mContext: Context, val mList: List<AppointmentData>) : RecyclerView.Adapter<AppointmentRecyclerAdapter.AppointmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.appointment_list_item, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {

        val data = mList[position]

        holder.bind(mContext, data)

    }

    override fun getItemCount(): Int = mList.size

    class AppointmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val titleTxt = view.findViewById<TextView>(R.id.titleTxt)
        val dateTimeTxt = view.findViewById<TextView>(R.id.dateTimeTxt)
        val placeNameTxt = view.findViewById<TextView>(R.id.placeNameTxt)
        val viewPlaceMapBtn = view.findViewById<ImageView>(R.id.viewPlaceMapBtn)
        val rootLayout = view.findViewById<LinearLayout>(R.id.rootLayout)

        val dateTimeSdf = SimpleDateFormat("M/d a h:mm")

        fun bind(context: Context, data: AppointmentData) {

            titleTxt.text = data.title
            dateTimeTxt.text = dateTimeSdf.format(data.datetime)
            placeNameTxt.text = data.placeName

            viewPlaceMapBtn.setOnClickListener {

                val myIntent = Intent(context, ViewMapActivity::class.java)
                myIntent.putExtra("appointment", data)
                context.startActivity(myIntent)

            }

            rootLayout.setOnClickListener {
                val myIntent = Intent(context, ViewAppointmentDetailActivity::class.java)
                myIntent.putExtra("appointment", data)
                context.startActivity(myIntent)
            }

        }

    }

}