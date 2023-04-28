package com.example.dangillsa_kotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dangillsa_kotlin.databinding.ChTableItemBinding

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var datalist = mutableListOf<CheckData>()

    inner class MyViewHolder(private val binding: ChTableItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(checkData: CheckData) {
            binding.checkrNum.text = checkData.checkr_num.toString()
            binding.checkrName.text = checkData.checkr_name.toString()
            binding.checkrRole.text = checkData.checkr_role.toString()
            binding.checkrRole.text = checkData.checkr_depar.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ChTableItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = datalist.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(datalist[position])
    }
}