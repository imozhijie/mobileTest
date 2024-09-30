package com.mzj.mobiletest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BookingViewModel : ViewModel() {
    fun getBookingData(dataProvider: BookingDataProvider): LiveData<Booking?> {
        val result = MutableLiveData<Booking?>()
        viewModelScope.launch {
            result.postValue(dataProvider.getBookingData())
        }
        return result
    }
}
