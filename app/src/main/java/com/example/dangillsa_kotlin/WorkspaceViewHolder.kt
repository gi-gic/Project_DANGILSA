package com.example.dangillsa_kotlin

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class WorkspaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val timeTextView: TextView = itemView.findViewById(R.id.wp_time)
    private val zoneTextView: TextView = itemView.findViewById(R.id.wp_zone)
    private val contentTextView: TextView = itemView.findViewById(R.id.wp_content)
    private val cameraImageView: ImageView = itemView.findViewById(R.id.camera_img)

    fun bind(notification: Notification, context: Context) {
        timeTextView.text = notification.time
        zoneTextView.text = notification.zone
        contentTextView.text = notification.content
        cameraImageView.setImageResource(notification.cameraIcon)

        // 클릭 이벤트 리스너 추가
        cameraImageView.setOnClickListener {
            // 권한이 부여되어 있는지 확인
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 권한이 없는 경우 권한 요청
                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                // drawable에서 이미지 파일 경로
                val imagePath = R.drawable.tsetimg1
                // 이미지 파일을 Bitmap으로 변환
                val bitmap = BitmapFactory.decodeResource(context.resources, imagePath)

                // 새로운 ImageView 생성
                val imageView = ImageView(context)
                imageView.setImageBitmap(bitmap)

                // 이미지를 보여줄 새로운 AlertDialog 생성
                val builder = AlertDialog.Builder(context)
                builder.setView(imageView)
                builder.setPositiveButton("닫기", null)
                builder.show()
            }
        }
    }
}
