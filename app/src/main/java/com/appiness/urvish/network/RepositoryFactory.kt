package com.appiness.urvish.network

import com.appiness.urvish.network.repository.DataRepository

object RepositoryFactory {

    fun createDataRepository() : DataRepository {
        val webApi = RestUtil.instance.retrofit.create(WebApi::class.java)
        return DataRepository(webApi)
    }
}