package com.example.dangillsa_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckedTextView
import android.widget.TextView
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class JoinActivity : AppCompatActivity() {

    private val urls = "http://59.0.124.121:5000/join"

    private lateinit var input_id: TextView
    private lateinit var input_name: TextView
    private lateinit var input_team: TextView
    private lateinit var input_code: TextView
    private lateinit var input_depart: TextView
    private lateinit var input_phone: TextView
    private lateinit var input_pw: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        // CheckedTextView 객체 가져오기
        val checkedTextView1 = findViewById<CheckedTextView>(R.id.join_checked1)
        val checkedTextView2 = findViewById<CheckedTextView>(R.id.join_checked2)

        // CheckedTextView를 체크 가능하게 설정하기
        checkedTextView1.isClickable = true
        checkedTextView2.isClickable = true


        // 체크 이벤트 리스너 등록하기
        checkedTextView1.setOnClickListener {
            // CheckedTextView가 체크될 때 실행할 코드 작성
            checkedTextView1.isChecked = !checkedTextView1.isChecked // 체크 상태를 토글
        }
        checkedTextView2.setOnClickListener {
            // CheckedTextView가 체크될 때 실행할 코드 작성
            checkedTextView2.isChecked = !checkedTextView2.isChecked // 체크 상태를 토글
        }

        // 가입하기 버튼 Onclick
        findViewById<View>(R.id.join_join_btn).setOnClickListener {

            input_id = findViewById(R.id.editTextTextPersonName)
            input_name = findViewById(R.id.editTextTextPersonName3)
            input_team = findViewById(R.id.editTextTextPersonName6)
            input_code = findViewById(R.id.editTextTextPersonName7)
            input_depart = findViewById(R.id.department_edt)
            input_phone = findViewById(R.id.editTextTextPersonName9)
            input_pw = findViewById(R.id.editTextTextPersonName10)

            val id = input_id.text.toString()
            val name = input_name.text.toString()
            val team = input_team.text.toString()
            val code = input_code.text.toString()
            val depart = input_depart.text.toString()
            val phone = input_phone.text.toString()
            val pw = input_pw.text.toString()

            val client = OkHttpClient()

            // POST 요청 바디 생성
            val jsonObject = JSONObject()
            jsonObject.put("id", id)
            jsonObject.put("name", name)
            jsonObject.put("team", team)
            jsonObject.put("code", code)
            jsonObject.put("depart", depart)
            jsonObject.put("phone", phone)
            jsonObject.put("pw", pw)

            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = jsonObject.toString().toRequestBody(mediaType)

            // POST 요청 생성
            val request = Request.Builder()
                .url(urls)
                .post(requestBody)
                .build()

            // Make a call with the request
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        val responseData = response.body?.string()
                        // Handle the response data
                    }
                }
            })

            Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
            // Intent 객체 생성
            val intent = Intent(this, LoginGeneralActivity::class.java)
            // 다른 액티비티로 이동
            startActivity(intent)
            // 현재 액티비티 종료
            finish()
        }


        // 뒤로가기 버튼 Onclick
        // TextView 객체 가져오기
        val textView = findViewById<TextView>(R.id.join_back)

        // 클릭 이벤트 리스너 등록하기
        textView.setOnClickListener {
            // 다른 액티비티로 이동하기 위한 Intent 생성
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) // 다른 액티비티 호출
        }

    }
}