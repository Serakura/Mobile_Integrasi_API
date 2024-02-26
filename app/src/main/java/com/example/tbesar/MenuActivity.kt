package com.example.tbesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.tbesar.storage.SharedPrefManager

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val nm = findViewById<TextView>(R.id.sw_name)
            val data = SharedPrefManager.getInstance(this).user
            nm.text=data.nama
        }
        val btn = findViewById<Button>(R.id.btn_logout)
        btn.setOnClickListener(){
            SharedPrefManager.getInstance(this).clear()
            val intent = Intent(this,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

    }
    override fun onStart() {
        super.onStart()
        if(!SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(this@MenuActivity,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}