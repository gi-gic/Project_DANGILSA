package com.example.dangillsa_kotlin

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationBuilderWithBuilderAccessor
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class NotiTriggerActivity : AppCompatActivity() {

    var CHANNEL_ID ="Android tutorial"
    var CHANNEL_NAME = "Android_notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noti_trigger)

        findViewById<Button>(R.id.notify_btn).setOnClickListener{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                var notificationBuilder : Notification.Builder? = null
                val importance = NotificationManager.IMPORTANCE_HIGH
                val notificationChannel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME,importance)
                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ex_image)

                // 프래그먼트로 이동하기 위한 intent 생성
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("fragment_name", "helmet")
                }
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(this, Calendar.getInstance().timeInMillis.toInt(),
                    intent,0)
                notificationBuilder = Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle("제 1구역: 보호구 탈착 발생!")
                    .setContentText("보호장비 착용을 확인해주세요.")
                    .setSmallIcon(R.drawable.logo_orange)
                    .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.notification_on))
                    .setStyle(Notification.BigPictureStyle(notificationBuilder).bigPicture(bitmap))
                    .addAction(R.drawable.notification_on, "확인", pendingIntent)

                val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager

                notificationManager.createNotificationChannel(notificationChannel)
                notificationManager.notify(0, notificationBuilder.build())

            } else {
                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ex_image)

                val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager

                val bigPictureNotification = NotificationCompat.BigPictureStyle()
                bigPictureNotification.bigPicture(bitmap).build()

                // 프래그먼트로 이동하기 위한 intent 생성
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("gotoFragment", "helmet")

                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(this, Calendar.getInstance().timeInMillis.toInt(),
                    intent,0)

                val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.notification_on)
                val notificationBuilder = NotificationCompat.Builder(this)
                    .setContentTitle("제 1구역: 보호구 탈착 발생!")
                    .setContentText("보호장비 착용을 확인해주세요.")
                    .setSmallIcon(R.drawable.logo_orange)
                    .setLargeIcon(largeIcon) // 큰 아이콘 설정
                    .setStyle(bigPictureNotification)
                    .addAction(R.drawable.notification_on, "Show activity", pendingIntent)

                notificationManager.notify(0,notificationBuilder.build())
            }
        }




    }// onCreate
}