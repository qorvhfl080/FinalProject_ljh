package com.nepplus.finalproject_ljh.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nepplus.finalproject_ljh.R
import com.nepplus.finalproject_ljh.databinding.ActivityViewMyPlaceListBinding
import com.nepplus.finalproject_ljh.databinding.MyPlaceListItemBinding
import com.nepplus.finalproject_ljh.datas.PlaceData

class MyPlaceRecyclerAdapter(val mContext: Context, val mList: List<PlaceData>) : RecyclerView.Adapter<MyPlaceRecyclerAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val binding = MyPlaceListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MyViewHolder(binding)
        val view = LayoutInflater.from(mContext).inflate(R.layout.my_place_list_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = mList.size

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val placeNameTxt = view.findViewById<TextView>(R.id.placeNameTxt)
        val isPrimaryTxt = view.findViewById<TextView>(R.id.isPrimaryTxt)
        val viewPlaceMapBtn = view.findViewById<ImageView>(R.id.viewPlaceMapBtn)

    }

}