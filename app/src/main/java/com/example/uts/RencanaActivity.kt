package com.example.uts

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.uts.databinding.ActivityRencanaBinding
import java.util.Arrays

class RencanaActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityRencanaBinding
    companion object {
        const val COM_TANGGAL = "tanggal"
        const val COM_ASAL = "asal"
        const val COM_TUJUAN = "tujuan"
        const val COM_PAKET = "paket"
    }
    var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityRencanaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            var asal = ""
            var tujuan = ""
            var hargaAsal = 0
            var hargaTujuan = 0
            var hargaKelas = 0
            var harga = 0
            txtPrice.text = String.format("Rp%,d", harga)
            val cardPaketViews = arrayOf(cardPaket1, cardPaket2, cardPaket3, cardPaket4, cardPaket5, cardPaket6, cardPaket7)
            val paketState = BooleanArray(cardPaketViews.size)
            Arrays.fill(paketState, false)

            inputDate.setOnClickListener {
                val datePicker = DatePicker()
                datePicker.show(supportFragmentManager, "datePicker")
            }

            spinnerAsal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val arrayStasiun = resources.getStringArray(R.array.stasiun)
                    val arrayHarga = resources.getStringArray(R.array.nilai_stasiun)
                    asal = arrayStasiun[position]
                    harga -= hargaAsal
                    hargaAsal = Integer.parseInt(arrayHarga[position])
                    harga += hargaAsal
                    txtPrice.text = String.format("Rp%,d", harga)
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            spinnerTujuan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val arrayStasiun = resources.getStringArray(R.array.stasiun)
                    val arrayHarga = resources.getStringArray(R.array.nilai_stasiun)
                    tujuan = arrayStasiun[position]
                    harga -= hargaTujuan
                    hargaTujuan = Integer.parseInt(arrayHarga[position])
                    harga += hargaTujuan
                    txtPrice.text = String.format("Rp%,d", harga)
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            spinnerKelas.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val arrayKelas = resources.getStringArray(R.array.nilai_kelas)
                    harga -= hargaKelas
                    hargaKelas = Integer.parseInt(arrayKelas[position])
                    harga += hargaKelas
                    txtPrice.text = String.format("Rp%,d", harga)
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            for ((index, cardPaketView) in cardPaketViews.withIndex()) {
                cardPaketView.setOnClickListener {
                    cardPaketView.showNext()
                    paketState[index] = !paketState[index]
                    if (paketState[index]) {
                        harga += 10000
                    } else {
                        harga -= 10000
                    }
                    txtPrice.text = String.format("Rp%,d", harga)
                }
            }

            btnOrder.setOnClickListener {

                if (selectedDate == "") {
                    Toast.makeText(
                        this@RencanaActivity, "Mohon pilh tanggal", Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        this@RencanaActivity, "Tiket berhasil dipesan", Toast.LENGTH_SHORT
                    ).show()

                    val paketList = resources.getStringArray(R.array.paket)
                    val selectedPaket = StringBuilder()
                    for (index in paketState.indices) {
                        if (paketState[index]) {
                            if (selectedPaket.isNotEmpty()) {
                                selectedPaket.append(", ")
                            }
                            selectedPaket.append(paketList[index])
                        }
                    }
                    var finalPaket = "-"
                    finalPaket = selectedPaket.toString()

                    val intentToDashboardActivity =
                        Intent(this@RencanaActivity, DashboardActivity::class.java)
                    intentToDashboardActivity.putExtra(COM_TANGGAL, selectedDate)
                    intentToDashboardActivity.putExtra(COM_ASAL, asal)
                    intentToDashboardActivity.putExtra(COM_TUJUAN, tujuan)
                    intentToDashboardActivity.putExtra(COM_PAKET, finalPaket)
                    startActivity(intentToDashboardActivity)
                    finish()
                }
            }
        }
    }

    override fun onDateSet(p0: android.widget.DatePicker?, year: Int, month: Int, date: Int) {
        selectedDate = "$date/${month + 1}/$year"
        with(binding) {
            inputDate.text = selectedDate
        }
    }
}