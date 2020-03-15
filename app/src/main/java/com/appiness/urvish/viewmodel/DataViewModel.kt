package com.appiness.urvish.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appiness.urvish.network.model.DataModel
import com.appiness.urvish.network.repository.DataRepository
import java.util.*

class DataViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val dataResponse: MutableLiveData<List<DataModel>> = MutableLiveData()

    val responseModel: LiveData<List<DataModel>> = dataResponse

    @SuppressLint("CheckResult")
    fun getDataFromServer() {
        dataRepository
            .getData()
            .subscribe {
                dataResponse.postValue(it)
            }
    }
}