package com.example.dangillsa_kotlin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.dangillsa_kotlin.databinding.ActivityObjectdetectionBinding


class ObjectDetectionActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityObjectdetectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityObjectdetectionBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        val result = intent.getStringExtra("Name")
        Log.d("Object Detection : Name", result.toString())

        // Name값 받아오고

        val intent = Intent (this, ObjectSuccessActivity::class.java)
        // 2 intent 지정하고
        // Name값 보내고
        intent.putExtra("Name", result)

    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }
}
