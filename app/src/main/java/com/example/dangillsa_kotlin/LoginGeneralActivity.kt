package com.example.dangillsa_kotlin

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class LoginGeneralActivity : AppCompatActivity() {
    private val urls = "http://59.0.124.121:5000/login"

    private lateinit var input_id: TextView

    private lateinit var input_pw: TextView
    val MY_PERMISSION_ACCESS_ALL = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_general)

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this,Manifest.permission.HIGH_SAMPLING_RATE_SENSORS) != PackageManager.PERMISSION_GRANTED
            )
        {
            var permissions = arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.POST_NOTIFICATIONS,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.HIGH_SAMPLING_RATE_SENSORS
            )
            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSION_ACCESS_ALL)
        }


        // 로그인 버튼 클릭 이벤트 리스너 등록

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
                                Toast.makeText(this@LoginGeneralActivity, "로그인 성공", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(this@LoginGeneralActivity, MainGeneralActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@LoginGeneralActivity, "로그인 실패", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                })
            }
        }

        // 회원가입 버튼 클릭 이벤트 리스너 등록
        findViewById<View>(R.id.join_btn).setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 아이디/비밀번호 찾기 버튼 클릭 이벤트 리스너 등록
        findViewById<TextView>(R.id.findidpw_btn).setOnClickListener {
            val intent = Intent(this, FindidpwActivity::class.java)
            startActivity(intent)
        }

//        // 관리자 회원가입 버튼 클릭 이벤트 리스너 등록
//        findViewById<TextView>(R.id.join_admin).setOnClickListener {
//            val intent = Intent(this, JoinAdminActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        // 관리자 로그인 버튼 클릭 이벤트 리스너 등록
        findViewById<TextView>(R.id.login_admin).setOnClickListener {
            // 비밀번호 입력 다이얼로그 띄우기
            showPasswordDialog()
        }
    }

    private fun showPasswordDialog() {
        // 다이얼로그 빌더 생성
        val builder = AlertDialog.Builder(this)

        // 레이아웃 파일 설정
        val view = LayoutInflater.from(this).inflate(R.layout.password_dialog, null)
        builder.setView(view)

        // 다이얼로그 제목 설정
        val title = view.findViewById<TextView>(R.id.dialog_title)
        title.text = "관리자 암호"

        // 대화상자의 배경을 수정하여 모서리를 둥글게 만들기
        val background = view.background
        if (background is GradientDrawable) {
            background.cornerRadius = resources.getDimension(R.dimen.dialog_corner_radius)
        }

        // 확인 버튼 클릭 이벤트 리스너 등록
        builder.setPositiveButton("확인") { dialog, which ->
            val passwordInput = view.findViewById<EditText>(R.id.password_input)
            val password = passwordInput.text.toString()

            if (password == "1234") {
                // 비밀번호가 일치하면 LoginActivity로 이동
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "잘못된 비밀번호입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // 취소 버튼 클릭 이벤트 리스너 등록
        builder.setNegativeButton("취소") { dialog, which -> }



        // 다이얼로그 보이기
        val dialog = builder.create()
        dialog.show()

        // 확인 버튼과 취소 버튼의 텍스트 컬러 변경
        val positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        val negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)

        positiveButton.setTextColor(ContextCompat.getColor(this, R.color.gray))
        negativeButton.setTextColor(ContextCompat.getColor(this, R.color.gray))
    }
}

