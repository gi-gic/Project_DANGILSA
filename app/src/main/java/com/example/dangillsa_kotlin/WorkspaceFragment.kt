package com.example.dangillsa_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class WorkspaceFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WorkspaceAdapter

    private var notifications: MutableList<Notification> = mutableListOf(
        Notification("15:33", "1", "1구역:안전모가 탈착되었...", R.drawable.camera_orange),
        Notification("15:02", "2", "2구역:안전모가 탈착되었...", R.drawable.camera_orange),
        Notification("14:35", "2", "2구역:안전모가 탈착되었...", R.drawable.camera_orange),
        Notification("14:12", "2", "2구역:안전모가 탈착되었...", R.drawable.camera_orange),
        Notification("13:51", "1", "1구역:안전모가 탈착되었...", R.drawable.camera_orange)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_workspace, container, false)

        recyclerView = view.findViewById(R.id.ch_recycler_view)
        adapter = WorkspaceAdapter(notifications)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return view
    }


}

