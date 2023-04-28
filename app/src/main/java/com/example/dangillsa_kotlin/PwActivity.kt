package com.example.dangillsa_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class PwActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pw)


        // 뒤로가기 버튼 Onclick
        // TextView 객체 가져오기
        val textView = findViewById<TextView>(R.id.pw_back)

        // 클릭 이벤트 리스너 등록하기
        textView.setOnClickListener {
            // 다른 액티비티로 이동하기 위한 Intent 생성
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) // 다른 액티비티 호출
        }


        // 비밀번호찾기 Onclick
        findViewById<View>(R.id.pw_change_btn).setOnClickListener {


            Toast.makeText(this, "비밀번호가 변경되었습니다.", Toast.LENGTH_SHORT).show()
            // Intent 객체 생성
            val intent = Intent(this,LoginActivity::class.java)
            // 다른 액티비티로 이동
            startActivity(intent)
            // 현재 액티비티 종료
            finish()
        }







    }
}