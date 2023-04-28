package com.example.dangillsa_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    private val urls = "http://59.0.124.121:5000/alogin"

    private lateinit var input_id: TextView
    private lateinit var input_pw: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<View>(R.id.login_btn).setOnClickListener {
            input_id = findViewById(R.id.editTextTextPersonName2)
            input_pw = findViewById(R.id.editTextTextPassword)

            val id = input_id.text.toString()
            val pw = input_pw.text.toString()

            if (id.isEmpty() || pw.isEmpty()) {
                Toast.makeText(this, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                // OkHttp 클라이언트 객체 생성
                val client = OkHttpClient()

                // POST 요청 바디 생성
                val jsonObject = JSONObject()
                jsonObject.put("id", id)
                jsonObject.put("pw", pw)

                val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
                val requestBody = jsonObject.toString().toRequestBody(mediaType)

                // POST 요청 생성
                val request = Request.Builder()
                    .url(urls)
                    .post(requestBody)
                    .build()

                // 요청 보내기
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body?.string()

                        runOnUiThread {
                            // 응답 데이터 처리
                            val json = JSONObject(responseData)
                            val success = json.getBoolean("success")
                            val message = json.getString("message")

                            if (success) {
                                Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                })
            }
        }

        // 회원가입 Onclick

        findViewById<View>(R.id.join_btn).setOnClickListener {

            val intent = Intent(this, JoinAdminActivity::class.java)
            // 다른 액티비티로 이동
            startActivity(intent)
            // 현재 액티비티 종료
            finish()
        }


        // 일반 Onclick
        findViewById<View>(R.id.login_general_go).setOnClickListener {
            // Intent 객체 생성
            val intent = Intent(this, LoginGeneralActivity::class.java)
            // 다른 액티비티로 이동
            startActivity(intent)
            // 현재 액티비티 종료
            finish()
        }


        // 아이디/비밀번호찾기 Onclick
        // TextView 객체 가져오기
        val findidBtn = findViewById<TextView>(R.id.findidpw_btn)

        // 클릭 이벤트 리스너 등록하기
        findidBtn.setOnClickListener {
            // 다른 액티비티로 이동하기 위한 Intent 생성
            val intent = Intent(this, FindidpwActivity::class.java)
            startActivity(intent) // 다른 액티비티 호출
        }
    }

}