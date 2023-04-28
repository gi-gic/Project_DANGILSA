package com.example.dangillsa_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList: ArrayList<Notifications>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>(){








    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ch_table_item,
        parent,false)
        return MyViewHolder(itemView)

    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

//        holder.Name.text = currentitem.name
//        holder.Role.text = currentitem.role
//        holder.Depar.text = currentitem.department

    }

    override fun getItemCount(): Int {
        return userList.size
    }




    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val Name : TextView = itemView.findViewById(R.id.checkr_name)
        val Role : TextView = itemView.findViewById(R.id.checkr_role)
        val Depar : TextView = itemView.findViewById(R.id.checkr_depar)
    }






}