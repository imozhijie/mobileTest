package com.mzj.mobiletest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var bookingDataManager: BookingDataManager
    private lateinit var viewModel: BookingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bookingDataManager = BookingDataManager(this)
        viewModel = ViewModelProvider(this).get(BookingViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        getBookingData()
    }

    private fun getBookingData() {
        viewModel.getBookingData(bookingDataManager).observe(this) { booking ->
            booking?.let { println(it) }
        }
    }

}