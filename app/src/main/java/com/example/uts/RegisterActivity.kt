package com.example.uts

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.uts.databinding.ActivityRegisterBinding
import java.util.Calendar

class RegisterActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityRegisterBinding
    companion object {
        const val COM_EMAIL = "email"
        const val COM_USERNAME = "username"
        const val COM_PASSWORD = "password"
    }
    private var isDateValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            inputBirth.setOnClickListener {
                val datePicker = DatePicker()
                datePicker.show(supportFragmentManager, "datePicker")
            }

            btnRegister.setOnClickListener {
                val email = inputEmail.text.toString()
                val username = inputUsername.text.toString()
                val password = inputPassword.text.toString()

                if (email == "" || username == "" || password == "") {
                    Toast.makeText(
                        this@RegisterActivity, "Input tidak boleh kosong", Toast.LENGTH_SHORT
                    ).show()
                } else if (!isDateValid) {
                    Toast.makeText(
                        this@RegisterActivity, "Umur tidak cukup", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@RegisterActivity, "Registrasi berhasil", Toast.LENGTH_SHORT
                    ).show()
                    val intentToLoginActivity = Intent(this@RegisterActivity, LoginActivity::class.java)
                    intentToLoginActivity.putExtra(COM_EMAIL, email)
                    intentToLoginActivity.putExtra(COM_USERNAME, username)
                    intentToLoginActivity.putExtra(COM_PASSWORD, password)
                    startActivity(intentToLoginActivity)
                    finish()
                }
            }
        }
    }

    override fun onDateSet(p0: android.widget.DatePicker?, year: Int, month: Int, date: Int) {
        with(binding) {
            inputBirth.text = "$date/${month + 1}/$year"
        }
        val calendar = Calendar.getInstance()
        isDateValid = calendar.get(Calendar.YEAR) - 15 > year
    }
}