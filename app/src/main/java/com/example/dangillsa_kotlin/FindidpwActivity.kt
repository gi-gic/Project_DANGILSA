package com.example.dangillsa_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class FindidpwActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findidpw)


// 뒤로가기 버튼 Onclick
        // TextView 객체 가져오기
        val textView = findViewById<TextView>(R.id.findidpw_back)

        // 클릭 이벤트 리스너 등록하기
        textView.setOnClickListener {
            // 다른 액티비티로 이동하기 위한 Intent 생성
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) // 다른 액티비티 호출
        }


        // 아이디찾기 Onclick
        findViewById<View>(R.id.findid_btn).setOnClickListener {
            // Intent 객체 생성
            val intent = Intent(this,IdActivity::class.java)
            // 다른 액티비티로 이동
            startActivity(intent)
            // 현재 액티비티 종료
            finish()
        }

        // 비밀번호 Onclick
        findViewById<View>(R.id.findpw_btn).setOnClickListener {
            // Intent 객체 생성
            val intent = Intent(this,PwActivity::class.java)
            // 다른 액티비티로 이동
            startActivity(intent)
            // 현재 액티비티 종료
            finish()
        }












    }
}