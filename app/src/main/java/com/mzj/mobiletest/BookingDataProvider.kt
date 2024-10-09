package com.mzj.mobiletest

interface BookingDataProvider {
    suspend fun getBookingData(): Booking?
    fun getBookingCachedData(): Booking?
    fun isCacheExpired(): Boolean
}