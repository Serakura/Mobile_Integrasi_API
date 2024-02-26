package com.example.tbesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tbesar.api.RetrofitClient
import com.example.tbesar.model.register.register
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val et_name = findViewById<EditText>(R.id.txt_reg_name)
        val et_phone = findViewById<EditText>(R.id.txt_reg_phone)
        val et_username = findViewById<EditText>(R.id.txt_reg_username)
        val et_password = findViewById<EditText>(R.id.txt_reg_password)

        val buttonRegister = findViewById<Button>(R.id.button_register)
        buttonRegister.setOnClickListener{
            val nama = et_name.text.toString().trim()
            val telp = et_phone.text.toString().trim()
            val username = et_username.text.toString().trim()
            val password = et_password.text.toString().trim()

            if(nama.isEmpty()){
                et_name.error="Name required"
                et_name.requestFocus()
                return@setOnClickListener
            }
            if(telp.isEmpty()){
                et_phone.error="Number Phone required"
                et_phone.requestFocus()
                return@setOnClickListener
            }
            if(username.isEmpty()){
                et_username.error="Username required"
                et_username.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                et_password.error="Password required"
                et_password.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.createUser(nama, telp, username, password)
                .enqueue(object: Callback<register>{
                override fun onResponse(call: Call<register>, response: Response<register>) {
                    if(response.body() != null && response.isSuccessful() && response.body()?.result_code == true){
                    Toast.makeText(applicationContext, response.body()?.status, Toast.LENGTH_LONG).show()

                    val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
                    startActivity(intent)

                    } else {
                        android.widget.Toast.makeText(applicationContext, response.body()?.status, android.widget.Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<register>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

            })

        }

    }
}