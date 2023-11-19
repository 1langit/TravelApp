package com.example.uts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import com.example.uts.databinding.ActivityLoginBinding
import com.example.uts.RegisterActivity.Companion.COM_EMAIL
import com.example.uts.RegisterActivity.Companion.COM_USERNAME
import com.example.uts.RegisterActivity.Companion.COM_PASSWORD

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            var email = data?.getStringExtra(COM_EMAIL)
            var username = data?.getStringExtra(COM_USERNAME)
            var password = data?.getStringExtra(COM_PASSWORD)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val res_email = intent.getStringExtra(COM_EMAIL)
        val res_username = intent.getStringExtra(COM_USERNAME)
        val res_password = intent.getStringExtra(COM_PASSWORD)

        with(binding) {
            btnLogin.setOnClickListener {
                val email = inputEmail.text.toString()
                val password = inputPassword.text.toString()

                if (email == res_email || email == res_username) {
                    if (password == res_password) {
                        Toast.makeText(
                            this@LoginActivity, "Login berhasil", Toast.LENGTH_SHORT
                        ).show()
                        val intentToDashboardActivity = Intent(this@LoginActivity, DashboardActivity::class.java)
                        startActivity(intentToDashboardActivity)
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity, "Password salah", Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@LoginActivity, "Email atau username salah", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}