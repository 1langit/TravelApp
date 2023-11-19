package com.example.uts

import android.content.Intent
import android.graphics.Typeface
import android.icu.lang.UProperty.INT_START
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.uts.RencanaActivity.Companion.COM_ASAL
import com.example.uts.RencanaActivity.Companion.COM_PAKET
import com.example.uts.RencanaActivity.Companion.COM_TANGGAL
import com.example.uts.RencanaActivity.Companion.COM_TUJUAN
import com.example.uts.databinding.ActivityDashboardBinding


class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val res_tanggal = intent.getStringExtra(COM_TANGGAL)
        val res_asal = intent.getStringExtra(COM_ASAL)
        val res_tujuan = intent.getStringExtra(COM_TUJUAN)
        val res_paket = intent.getStringExtra(COM_PAKET)

        with(binding) {
            btnAdd.setOnClickListener {
                val intentToRencanaActivity = Intent(this@DashboardActivity, RencanaActivity::class.java)
                startActivity(intentToRencanaActivity)
                finish()
            }

            inputCalendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                if (selectedDate == res_tanggal) {
                    Toast.makeText(
                        this@DashboardActivity, "1 pesanan tiket di tanggal ini", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@DashboardActivity, "jadwalmu kosong, pesan tiket sekarang!", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            if (res_tanggal == null) {
                rencanaKosong.visibility = View.VISIBLE
                rencanaTerakhir.visibility = View.GONE
            } else {
                rencanaKosong.visibility = View.GONE
                rencanaTerakhir.visibility = View.VISIBLE

                txtTanggal.text = res_tanggal
                txtAsal.text = "Dari $res_asal"
                txtTujuan.text = "Ke $res_tujuan"
                if (res_paket != "") {
                    txtPaket.text = "Paket dipilih: \n$res_paket"
                }
            }
        }
    }
}