package com.example.dangillsa_kotlin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.NotificationCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.navigation.NavigationView
import java.util.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


private const val CHANNEL_ID = "my_channel"
private const val CHANNEL_NAME = "My Channel"
private const val NOTIFICATION_ID = 1



class HelmetFragment : Fragment() {


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    // cam
    private lateinit var img: ImageView



    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_helmet, container, false)
        val notiTriggerText = view.findViewById<TextView>(R.id.noti_trigger_text)
        notiTriggerText.setOnClickListener {
            createNotification()
        }

// cam 1.
//        val view2 = inflater.inflate(R.layout.fragment_helmet, container, false)
         img = view.findViewById<ImageView>(R.id.img)
        getImageFromServer(url = "http://222.102.104.190:8888/image")
        // Inflate the layout for this fragment





        return view
    }
    // push 알림 코드 -- maingeneral activity에도 추가 코드 필요함★
    private fun createNotification() {
        // Create a notification channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME,importance)
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.warning_fall)

            // 프래그먼트로 이동하기 위한 intent 생성
            val intent = Intent(requireContext(), MainGeneralActivity::class.java).apply {
                putExtra("fragment_name", "fall")
            }
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

            val pendingIntent = PendingIntent.getActivity(requireContext(), Calendar.getInstance().timeInMillis.toInt(),
                intent,0)

            // 추가된 부분
            val helmetFragmentIntent = Intent(requireContext(), MainGeneralActivity::class.java).apply {
                putExtra("fragment_name", "helmet")
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val helmetFragmentPendingIntent = PendingIntent.getActivity(requireContext(), 0,
                helmetFragmentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val notificationBuilder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setContentTitle("! 낙상사고 발생 !")
                .setContentText("인원체크 및 위치를 확인해주세요.")
                .setSmallIcon(R.drawable.logo_orange)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.warning_fall))
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .addAction(R.drawable.warning_fall, "확인", pendingIntent)
            // 추가된 부분
//                .addAction(R.drawable.notification_on, "보호구 착용 확인", helmetFragmentPendingIntent)

            val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())

        } else {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.warning_fall)

            val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

            val bigPictureNotification = NotificationCompat.BigPictureStyle()
            bigPictureNotification.bigPicture(bitmap).build()

            // 프래그먼트로 이동하기 위한 intent 생성
            val intent = Intent(requireContext(), MainGeneralActivity::class.java)
            intent.putExtra("gotoFragment", "fall")

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(requireContext(), Calendar.getInstance().timeInMillis.toInt(),
                intent,0)

            // 추가된 부분
            val helmetFragmentIntent = Intent(requireContext(), MainGeneralActivity::class.java).apply {
                putExtra("fragment_name", "fall")
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val helmetFragmentPendingIntent = PendingIntent.getActivity(requireContext(), 0,
                helmetFragmentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.warning_fall)
            val notificationBuilder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setContentTitle("! 낙상사고 발생 !")
                .setContentText("인원체크 및 위치를 확인해주세요.")
                .setSmallIcon(R.drawable.logo_orange)
                .setLargeIcon(largeIcon)
                .setStyle(bigPictureNotification)
                .addAction(R.drawable.warning_fall, "확인", pendingIntent)
            // 추가된 부분
//                .addAction(R.drawable.notification_on, "보호구 착용 확인", helmetFragmentPendingIntent)

            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HelmetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        fun newInstance(): HelmetFragment {
            return HelmetFragment()
        }
    }


    private fun getImageFromServer(url: String) {
        // HTTP 요청 보내기


        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    // 이미지 파일 처리
                    val inputStream = response.body!!.byteStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)


                    // UI 업데이트
                    val handler = Handler(Looper.getMainLooper())
                    handler.post {
                        Log.d("result", "넘어옴")
                        img.setImageBitmap(bitmap)
                    }
                }
            }
        })

    }









}// fragment