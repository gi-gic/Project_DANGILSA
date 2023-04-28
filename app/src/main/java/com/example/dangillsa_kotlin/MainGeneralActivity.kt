package com.example.dangillsa_kotlin

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import android.os.PersistableBundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs
import kotlin.math.pow
import org.w3c.dom.Text
import kotlin.math.sqrt



class MainGeneralActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lastUpdate = 0L
    private var xValue: Float = 0f
    private var yValue: Float = 0f
    private var zValue :Float = 0f

    private val updateInterval = 10000L

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navigationView: NavigationView

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onResume() {
        super.onResume()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_FASTEST
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    private lateinit var sensorimageView: ImageView

    private lateinit var sensortextview: TextView
    private lateinit var sensortextmain: TextView
    private lateinit var sensortext_01: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_general)

        sensortextview = findViewById(R.id.sendortextView)
        sensorimageView = findViewById(R.id.noti_trigger_icon)
        sensortextmain = findViewById(R.id.sensortextmain)
        sensortext_01 = findViewById(R.id.sensortext_01)

        drawerLayout = findViewById(R.id.drawerLayout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.navigationView)

        setSupportActionBar(toolbar)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // 2. fragment
//        navController = findNavController(R.id.fragment2)
//        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navigationView.setupWithNavController(navController)// 네비게이션에 컨트롤러 연결

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_my_general -> {
                    navController.navigate(R.id.nav_my_general)
                }

                R.id.nav_logout -> {
                    startActivity(Intent(this, LoginGeneralActivity::class.java))
                    finish() // 현재 액티비티를 종료하여 뒤로 가기 버튼을 눌러도 다시 돌아오지 않도록 함
                }
            }

            drawerLayout.closeDrawer(GravityCompat.START)

            true
        }

        supportActionBar?.setDisplayShowTitleEnabled(false)

        val notificationIcon: ImageView = findViewById(R.id.notification_icon)
        notificationIcon.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

// push-notification 프래그먼트 이동 (intent)
//        when (intent?.getStringExtra("fragment_name")) {
//            "helmet" -> {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment2, HelmetFragment())
//                    .commit()
//            }


            // 다른 Fragment들도 추가 가능
//            "fall" -> {
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment2,FallFragment())
//                    .commit()
//            }



//            else -> {
//                // 기본적으로 MainFragment를 띄움
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment2, MainGeneralFragment())
//                    .commit()
//            }
//        }


    }// on create


//    private fun replaceFragment(fragment: Fragment) {
//        supportFragmentManager.commit {
//            replace(R.id.fragment2, fragment)
//            addToBackStack(null)
//        }
//    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    //여기서부터 센서 기능~
    override fun onSensorChanged(event: SensorEvent?) {
        event?.let{
            val x: Float = event.values[0]
            val y: Float = event.values[1]
            val z: Float = event.values[2]
            val f: Float = sqrt(x.pow(2) + y.pow(2) + z.pow(2))
            val now = System.currentTimeMillis()

            if (now - lastUpdate >= updateInterval) {
                lastUpdate = now
                Log.d("TAG", "x: $x, y: $y, z:$z")
            }

            if (xValue == 0f || yValue == 0f || zValue == 0f) {
                xValue = x
                yValue = y
                zValue = z
            }

            if (abs(x - xValue) > 2 && abs(y - yValue) > 2 && f > 20) {
                // && f > 20
                CoroutineScope(Dispatchers.IO).launch {
                    val status = "사람이 넘어졌습니다."
                    val response = SendStatusTask().sendStatus(status)
                    Log.d("TAG", status)
                    withContext(Dispatchers.Main) {
                        // 서버로 결과값을 보내는 작업이 완료되면 앱으로 결과값을 보내줌
                        // 이 부분을 추가합니다.
                        sensortextview.text = response
                        sensorimageView.setImageResource(R.drawable.warning_fall)
                        sensortextmain.text = "낙상사고 발생!\n인원체크 및 위치를 확인해주세요."
                        sensortextmain.setTextColor(ContextCompat.getColor(this@MainGeneralActivity, R.color.bounding_box_color))
                        sensortext_01.text = "같은 현장에 계신 직원분들께서는 경고알림 발생시, \n주의 당부사항을 꼭 지켜주세요. \n\n예) 안전구 탈착시 - 재착용, 낙상 발생시 - 위치확인"
                    }
                }
                xValue = x
                yValue = y
                zValue = z
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

}