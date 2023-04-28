package com.example.dangillsa_kotlin

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt
import android.hardware.SensorEventListener


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val CHANNEL_ID = "my_channel"
private const val CHANNEL_NAME = "My Channel"
private const val NOTIFICATION_ID = 1

class MainGeneralFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var notiTriggerIcon: ImageView

    // senseor 1.
    private lateinit var sensorManager: SensorManager
    private var lastUpdate = 0L
    private var xValue: Float = 0f
    private var yValue: Float = 0f

    private val updateInterval = 10000L

    private lateinit var textView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            val fragmentName = it.getString("fragment_name")
//            if (fragmentName != null) {
//                when (fragmentName) {
//                    "helmet" -> {
//                        // HelmetFragment를 생성하여 화면에 표시
//                        val helmetFragment = HelmetFragment()
//                        requireActivity().supportFragmentManager.beginTransaction()
//                            .replace(R.id.fragment2, helmetFragment)
//                            .commit()
//                    }
                    // 다른 Fragment들을 추가로 처리할 수 있음
//                    else -> {
                        // 예외 처리 로직 등을 추가할 수 있음
//                    }
//                }
//            }
//        }
    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_main_general, container, false)
//        notiTriggerIcon = view.findViewById(R.id.noti_trigger_icon)
//        notiTriggerIcon.setOnClickListener {
//            createNotification()
//        }
//        return view
//    }


    // push 알림 코드 -- maingeneral activity에도 추가 코드 필요함★
    private fun createNotification() {
        // Create a notification channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME,importance)
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ex_image)

            // 프래그먼트로 이동하기 위한 intent 생성
            val intent = Intent(requireContext(), MainGeneralActivity::class.java).apply {
                putExtra("fragment_name", "helmet")
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
                .setContentTitle("제 1구역: 보호구 탈착 발생!")
                .setContentText("보호장비 착용을 확인해주세요.")
                .setSmallIcon(R.drawable.logo_orange)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.notification_on))
                .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
//                .addAction(R.drawable.notification_on, "확인", pendingIntent)
                // 추가된 부분
                .addAction(R.drawable.notification_on, "보호구 착용 확인", helmetFragmentPendingIntent)

            val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

            notificationManager.createNotificationChannel(notificationChannel)
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())

        } else {
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ex_image)

            val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

            val bigPictureNotification = NotificationCompat.BigPictureStyle()
            bigPictureNotification.bigPicture(bitmap).build()

            // 프래그먼트로 이동하기 위한 intent 생성
            val intent = Intent(requireContext(), MainGeneralActivity::class.java)
            intent.putExtra("gotoFragment", "helmet")

            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(requireContext(), Calendar.getInstance().timeInMillis.toInt(),
                intent,0)

            // 추가된 부분
            val helmetFragmentIntent = Intent(requireContext(), MainGeneralActivity::class.java).apply {
                putExtra("fragment_name", "helmet")
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val helmetFragmentPendingIntent = PendingIntent.getActivity(requireContext(), 0,
                helmetFragmentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.notification_on)
            val notificationBuilder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setContentTitle("제 1구역: 보호구 탈착 발생!")
                .setContentText("보호장비 착용을 확인해주세요.")
                .setSmallIcon(R.drawable.logo_orange)
                .setLargeIcon(largeIcon)
                .setStyle(bigPictureNotification)
//                .addAction(R.drawable.notification_on, "확인", pendingIntent)
                // 추가된 부분
                .addAction(R.drawable.notification_on, "보호구 착용 확인", helmetFragmentPendingIntent)

            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
        }
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainGeneralFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}