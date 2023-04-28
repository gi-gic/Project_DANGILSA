package com.example.dangillsa_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class NotificationAdapter(private val notificationList : ArrayList<Notifications>):
    RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.notification_item,
        parent,false)


        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = notificationList[position]
        holder.notiImg.setImageResource(currentItem.notiImg)
        holder.notiText.text  = currentItem.notiText

    }

    override fun getItemCount(): Int {

        return notificationList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val notiImg : ImageView = itemView.findViewById(R.id.noti_img)
        val notiText : TextView = itemView.findViewById(R.id.noti_text)



    }


}