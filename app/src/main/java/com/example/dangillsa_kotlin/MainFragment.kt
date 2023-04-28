package com.example.dangillsa_kotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException

class MainFragment : Fragment() {

    private lateinit var card1: CardView
    private lateinit var card2: CardView
    private lateinit var card3: CardView
    private lateinit var card4: CardView
    private val CAMERA_REQUEST_CODE = 0
    private lateinit var currentBitmap: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        card1 = view.findViewById(R.id.card1)
        card2 = view.findViewById(R.id.card2)
        card3 = view.findViewById(R.id.card3)
        card4 = view.findViewById(R.id.card4)

        card1.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.nav_check)

        }

        card2.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.nav_cctv)
        }

        card3.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.nav_workspace)
        }

        card4.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.nav_paper)
        }

        val floatingBtn: FloatingActionButton = view.findViewById(R.id.floatingbtn)
        floatingBtn.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)

        }

        return view
    }

    private fun sendImageToServer(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream) // 이미지를 JPEG 형식으로 압축하여 스트림에 쓰기

        val byteArray = stream.toByteArray() // 스트림에서 바이트 배열로 변환
        val encodedString =
            Base64.encodeToString(byteArray, Base64.DEFAULT) // 바이트 배열을 Base64 문자열로 인코딩

        val url = "http://59.0.124.121:5000/re" // Flask 서버의 URL
        val request = Request.Builder()
            .url(url)
            .post(
                FormBody.Builder().add("image", encodedString).build()
            ) // POST 요청으로 인코딩된 이미지 데이터 전송
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                // 서버에서 전송한 응답 데이터 처리
                val jsonObject = JSONObject(responseBody!!)
                val result = jsonObject.getString("result")

                // Update UI on the main thread
                activity?.runOnUiThread {
                    if(result=="bowon"){
                        Toast.makeText(context, "보원님 출석 되었습니다", Toast.LENGTH_SHORT).show()
                        senddata(result)
                    }
                    if(result=="tak"){
                        Toast.makeText(context, "탁연님 출석 되었습니다", Toast.LENGTH_SHORT).show()
                        senddata(result)
                    }
                    if(result=="kjs"){
                        Toast.makeText(context, "정선님 출석 되었습니다", Toast.LENGTH_SHORT).show()
                        senddata(result)
                    }
                    if(result=="okj"){
                        Toast.makeText(context, "경진님 출석 되었습니다", Toast.LENGTH_SHORT).show()
                        senddata(result)
                    }
                    if(result=="jys"){
                        Toast.makeText(context, "예슬님 출석 되었습니다", Toast.LENGTH_SHORT).show()
                        senddata(result)
                    }
                    else{
                        val result = "Unknown"
                        Toast.makeText(context, "${result}님 출석 되었습니다.", Toast.LENGTH_SHORT).show()
                        senddata(result)
                    }
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                // 요청 실패 처리
            }
        })
    }

    fun senddata(result:String){
        Thread.sleep(2000)
        // MainFragment 에서 발생한 값을 보내주기만 한다.
        val intent = Intent(context, ObjectDetectionActivity::class.java)
        intent.putExtra("Name", result)
        context?.startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            currentBitmap = data?.extras?.get("data") as Bitmap
            sendImageToServer(currentBitmap)
        }
    }

}