package com.appiness.urvish.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.appiness.urvish.network.repository.DataRepository

class ViewModelFactory(private val dataRepository: DataRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DataViewModel(dataRepository) as T
    }
}