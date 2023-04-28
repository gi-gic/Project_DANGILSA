package com.example.dangillsa_kotlin

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class ServerCommunicator {
    private val client = OkHttpClient()

    //객체인식한 값을 서버로 보내는 함수
    // 보내야 할 것 :
    fun sendDetectionResults(user_oc: String, user_id: String) {
        Log.d("ㅇㅅㅇ", user_oc)
        Log.d("ㅇㅅㅇ", user_id)
        
        // Flask 서버로 이동
        val url = "http://59.0.124.121:5000/detection_results"

        // 요청 바디 생성
        val jsonObject = JSONObject()
        jsonObject.put("oc", user_oc)
        jsonObject.put("id", user_id)  // 'id' 필드 추가

        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val requestBody = jsonObject.toString().toRequestBody(mediaType)

        val request = Request.Builder().url(url).post(requestBody).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("요청", "요청 실패: $e")
            }
            override fun onResponse(call: Call, response: Response) {
                Log.d("요청", "요청 완료")
            }
        })
    }
}