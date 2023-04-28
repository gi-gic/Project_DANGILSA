package com.example.dangillsa_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NotificationActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Notifications>
    lateinit var notiImg: Array<Int>
    lateinit var notiText: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        notiImg = arrayOf(
            R.drawable.noti_img,
            R.drawable.noti_img2,
            R.drawable.noti_img3
        )

        notiText = arrayOf(
            "제 1구역: 06번 안전구가 탈착되었습니다. \n" +
                    "확인 부탁 드립니다.",
            "제 2구역: 12번 안전구가 탈착되었습니다.\n" +
                    "확인 부탁 드립니다.",
            "제 2구역: 10번 안전구가 탈착되었습니다. \n" +
                    "확인 부탁 드립니다."
        )

        newRecyclerView =findViewById(R.id.noti_recyclerview)
        newRecyclerView.layoutManager = LinearLayoutManager( this)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<Notifications>()
        getUserData()

// 뒤로가기 버튼 Onclick
        // TextView 객체 가져오기
        val textView = findViewById<TextView>(R.id.noti_back)

        // 클릭 이벤트 리스너 등록하기
        textView.setOnClickListener {
            // 다른 액티비티로 이동하기 위한 Intent 생성

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }//onCreate

    private fun getUserData() {
        for (i in notiImg.indices){
            val notifications = Notifications(notiText[i], notiImg[i])
            newArrayList.add(notifications)
        }
        newRecyclerView.adapter = NotificationAdapter(newArrayList)
    }
}