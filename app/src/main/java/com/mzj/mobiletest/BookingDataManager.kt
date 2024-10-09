package com.mzj.mobiletest

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.util.concurrent.TimeUnit

class BookingDataManager(private val context: Context) : BookingDataProvider {
    private var booking: Booking? = null
    private var lastFetchTime: Long = 0

    private val sharedPreferences = context.getSharedPreferences("Booking", Context.MODE_PRIVATE)
    private val gson = Gson()

    private val cacheTime: Long = TimeUnit.MINUTES.toMillis(5)

    companion object {
        private const val BOOKING_KEY = "BookingJson"
        private const val LAST_FETCH_TIME_KEY = "LastFetchTime"
    }

    init {
        initBookingCachedData()
    }

    private fun initBookingCachedData() {
        val json = sharedPreferences.getString(BOOKING_KEY, null)
        lastFetchTime = sharedPreferences.getLong(LAST_FETCH_TIME_KEY, 0)
        booking = json?.let { gson.fromJson(it, Booking::class.java) }
    }

    override suspend fun getBookingData(): Booking? {
        if (booking == null || isCacheExpired()) {
            refreshData()
        }
        return booking
    }

    override fun isCacheExpired(): Boolean {
        return System.currentTimeMillis() - lastFetchTime >= cacheTime
    }

    private fun refreshData() {
        fetchFromJson()?.let {
            booking = it
            lastFetchTime = System.currentTimeMillis()
            saveCachedData()
        }
    }

    private fun fetchFromJson(): Booking? {
        return try {
            context.assets.open("booking.json").bufferedReader().use { reader ->
                gson.fromJson(reader, Booking::class.java)
            }
        } catch (e: Exception) {
            Log.e("BookingDataManager", e.message ?: "fetchFromJson-Exception")
            null
        }
    }

    private fun saveCachedData() {
        with(sharedPreferences.edit()) {
            putString(BOOKING_KEY, gson.toJson(booking))
            putLong(LAST_FETCH_TIME_KEY, lastFetchTime)
            apply()
        }
    }

    override fun getBookingCachedData(): Booking? {
        return booking
    }
}
