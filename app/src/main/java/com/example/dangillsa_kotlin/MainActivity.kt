package com.example.dangillsa_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val navigationView: NavigationView = findViewById(R.id.navigationView)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // 2. fragment
        navController = findNavController(R.id.fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)// 네비게이션에 컨트롤러 연결

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_mywork1 -> {
                    navController.navigate(R.id.nav_mywork1)
                }
                R.id.nav_new_item -> {
                    navController.navigate(R.id.nav_mywork1)
                }
                R.id.nav_check -> {
                    navController.navigate(R.id.nav_check)
                }
                R.id.nav_cctv -> {
                    navController.navigate(R.id.nav_cctv)
                }
                R.id.nav_workspace -> {
                    navController.navigate(R.id.nav_workspace)
                }
                R.id.nav_paper -> {
                    navController.navigate(R.id.nav_paper)
                }
                R.id.nav_my -> {
                    navController.navigate(R.id.nav_my)
                }
                R.id.nav_logout -> {
                    startActivity(Intent(this, LoginGeneralActivity::class.java))
                    finish() // 현재 액티비티를 종료하여 뒤로 가기 버튼을 눌러도 다시 돌아오지 않도록 함
                }
            }

            drawerLayout.closeDrawer(GravityCompat.START)

            true
        }

        // 커스텀 툴바에서 이미지 뷰 찾기
        val notificationIcon: ImageView = findViewById(R.id.notification_icon)



        // 클릭 이벤트 리스너 등록
        notificationIcon.setOnClickListener {
            // 다른 액티비티로 이동
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)


        }
    }

    private fun displayMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    // 3. fragment
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
//

}