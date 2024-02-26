package com.example.tbesar

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.tbesar.api.RetrofitClient
import com.example.tbesar.model.login.login
import com.example.tbesar.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val tv_username = findViewById<TextView>(R.id.txt_username)
        val tv_password = findViewById<TextView>(R.id.txt_password)
        val button_sign_up = findViewById<Button>(R.id.button_sign_up)
        val button_sign_in = findViewById<Button>(R.id.button_sign_in)
        button_sign_up.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        button_sign_in.setOnClickListener {
            val username = tv_username.text.toString().trim()
            val password = tv_password.text.toString().trim()
            if(username.isEmpty()){
                tv_username.error="Username required"
                tv_username.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                tv_password.error="Password required"
                tv_password.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.userLogin(username, password)
                .enqueue(object : Callback<login>{
                    override fun onResponse(call: Call<login>, response: Response<login>) {
                        if(response.body() != null && response.isSuccessful() && response.body()?.result_code == true){
                        SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.data!!)
                        Toast.makeText(applicationContext, response.body()?.status, Toast.LENGTH_LONG).show()

                        val intent = Intent(this@LoginActivity,MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<login>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                })

        }
        buttonEffect(button_sign_up)
        buttonEffect(button_sign_in)
    }
    fun buttonEffect(button: View) {
        button.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background.setColorFilter(-0x1f0b8adf, PorterDuff.Mode.SRC_ATOP)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background.clearColorFilter()
                    v.invalidate()
                }
            }
            false
        }
    }

    override fun onStart() {
        super.onStart()
        if(SharedPrefManager.getInstance(this).isLoggedIn){

            val intent = Intent(this@LoginActivity,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}