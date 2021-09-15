package com.nepplus.colosseum.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.nepplus.finalproject_ljh.R
import com.nepplus.finalproject_ljh.ViewMapActivity
import com.nepplus.finalproject_ljh.datas.AppointmentData

class AppointmentAdapter(val mContext: Context, resId: Int, val mList: List<AppointmentData>) : ArrayAdapter<AppointmentData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            row = mInflater.inflate(R.layout.appointment_list_item, null)
        }
        row!!

        val data = mList[position]

        val title = row.findViewById<TextView>(R.id.titleTxt)
        val dateTime = row.findViewById<TextView>(R.id.dateTimeTxt)
        val place = row.findViewById<TextView>(R.id.placeNameTxt)
        val viewPlaceMap = row.findViewById<ImageView>(R.id.viewPlaceMapBtn)

        title.text = data.title
        dateTime.text = data.getFormattedDateTime()
        place.text = data.placeName
        viewPlaceMap.setOnClickListener {

            val myIntent = Intent(mContext, ViewMapActivity::class.java)
            myIntent.putExtra("appointment", data)
            mContext.startActivity(myIntent)

        }

        return row
    }

}