package com.appiness.urvish.network

import com.appiness.urvish.network.model.DataModel
import io.reactivex.Single
import retrofit2.http.GET

interface WebApi {

    @GET("api.json")
    fun getDataFromServer(): Single<List<DataModel>>
}