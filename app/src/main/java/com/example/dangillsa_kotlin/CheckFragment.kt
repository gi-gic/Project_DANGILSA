package com.example.dangillsa_kotlin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dangillsa_kotlin.databinding.FragmentCheckBinding
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException


class CheckFragment : Fragment() {

    private lateinit var binding: FragmentCheckBinding
    private lateinit var adapter: RecyclerViewAdapter
    private val mDatas = mutableListOf<CheckData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCheckBinding.inflate(inflater, container, false)
        initDogRecyclerView()
        initializelist()
        return binding.root
    }

    private fun initDogRecyclerView() {
        adapter = RecyclerViewAdapter()
        adapter.datalist = mDatas
        binding.chRecyclerView.adapter = adapter
        binding.chRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initializelist() {
        val url = "http://59.0.124.121:5000/cselect"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    Log.d("response", responseData + "")
                    try {
                        val jsonArray = JSONArray(responseData)
                        for (i in 0 until jsonArray.length()) {
                            val item = jsonArray.getJSONObject(i)
                            val id = item.getString("id")
                            val name = item.getString("name")
                            val role = item.getString("role")
                            val department = item.getString("department")
                            mDatas.add(CheckData(id, name, role, department))
                        }
                        requireActivity().runOnUiThread {
                            adapter.notifyDataSetChanged()
                        }
                    } catch (e: JSONException) {
                        Log.e("ERROR", "JSON 파싱 실패: ${e.message}")
                    }
                } else {
                    Log.e("ERROR", "API 요청 실패: ${response.code}")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("ERROR", "API 요청 실패: ${e.message}")
            }
        })
    }
}