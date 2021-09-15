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
import com.nepplus.finalproject_ljh.datas.PlaceData

class StartPlaceSpinnerAdapter(val mContext: Context, resId: Int, val mList: List<PlaceData>) : ArrayAdapter<PlaceData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            row = mInflater.inflate(R.layout.my_place_list_item, null)
        }
        row!!

        val data = mList[position]

        return row
    }

}