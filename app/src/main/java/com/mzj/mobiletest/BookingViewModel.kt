package com.mzj.mobiletest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookingViewModel : ViewModel() {
    fun getBookingData(dataProvider: BookingDataProvider): LiveData<Booking?> {
        val result = MutableLiveData<Booking?>()

        // 每次获取数据时，有本地缓存，先返回缓存(无论是否过期)
        // Return cached data first
        result.value = dataProvider.getBookingCachedData()

        if (dataProvider.isCacheExpired()) {
            // 数据过期后，自动触发刷新机制，获取新的数据替换旧有数据，并返回最新数据
            // Only fetch fresh data if cache is expired
            viewModelScope.launch(Dispatchers.IO) {
//                result.postValue(dataProvider.getBookingData())
                dataProvider.getBookingData()?.let { result.postValue(it) }
            }
        }

        return result
    }
}
