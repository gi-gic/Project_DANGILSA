package com.example.dangillsa_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.dangillsa_kotlin.databinding.ActivityMainBinding
import com.example.dangillsa_kotlin.ServerCommunicator

class ObjectSuccessActivity : AppCompatActivity() {
    // 받아와서
//    val result = intent.getStringExtra("Name")
//    val test = "당일사"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_object_success)

//        Log.d("Name", result.toString())

        val noButton: Button = findViewById(R.id.noButton)
        val yesButton: Button = findViewById(R.id.yesButton)

        noButton.setOnClickListener {
            Toast.makeText(this, "메인 페이지로 이동합니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }

        yesButton.setOnClickListener {
            exampleFunction()
            Toast.makeText(this, "문서 작성이 완료되었습니다.\n 잠시 후 메인 페이지로 이동합니다.", Toast.LENGTH_SHORT).show()
            try{
                Thread.sleep(1500)
            }catch (e:InterruptedException){
                e.printStackTrace()
            }
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun exampleFunction() {
//        val user_oc = "${test}"  // db에서 id 값에 맞는 값을 초기화해준다.
//        val user_id = "${result}" // DB나 방금 전 인식한 값에서 이름을 초기화해준다.

//        if(user_oc == null || user_id == null){
//            Toast.makeText(this, "부적절한 경로로 진입하셨습니다.", Toast.LENGTH_SHORT).show()
//        }else {
        Toast.makeText(this,"문서를 작성하는 중...",Toast.LENGTH_SHORT).show()
        ServerCommunicator().sendDetectionResults("이탁연","당일사")
//            ServerCommunicator().sendDetectionResults(user_oc, user_id)
//        }
    }
}
