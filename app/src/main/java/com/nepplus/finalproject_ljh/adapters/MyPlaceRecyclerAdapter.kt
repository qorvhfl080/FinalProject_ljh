package com.nepplus.finalproject_ljh.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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

        //val data = mList[position]

        holder.setRealData(mList[position])
        
        holder.viewPlaceMapBtn.setOnClickListener {
            Toast.makeText(mContext, "지도 버튼 눌림", Toast.LENGTH_SHORT).show()
        }
        
        holder.backgroundLayout.setOnClickListener {
            Toast.makeText(mContext, "${mList[position].name} 클릭됨", Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int = mList.size

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val placeNameTxt = view.findViewById<TextView>(R.id.placeNameTxt)
        val isPrimaryTxt = view.findViewById<TextView>(R.id.isPrimaryTxt)
        val viewPlaceMapBtn = view.findViewById<ImageView>(R.id.viewPlaceMapBtn)
        val backgroundLayout = view.findViewById<LinearLayout>(R.id.backgroundLayout)

        fun setRealData(data: PlaceData) {
            placeNameTxt.text = data.name
            if (data.isPrimary)
                isPrimaryTxt.visibility = View.VISIBLE
            else
                isPrimaryTxt.visibility = View.GONE

            //holder.viewPlaceMapBtn.setImageResource()
            
        }

    }

}