package com.example.dangillsa_kotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class WorkspaceAdapter(private val notifications: List<Notification>) : RecyclerView.Adapter<WorkspaceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkspaceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.workspace_table_item, parent, false)
        return WorkspaceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkspaceViewHolder, position: Int) {
        val notification = notifications[position]
        holder.bind(notification, holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return notifications.size
    }


}