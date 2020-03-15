package com.appiness.urvish.network.repository

import com.appiness.urvish.network.WebApi
import com.appiness.urvish.network.model.DataModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DataRepository (private val webApi: WebApi) {

    fun getData() : Observable<List<DataModel>> {

        return Observable.create { emitter ->
            webApi.getDataFromServer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( {
                    if (it.isNotEmpty()) {
                        emitter.onNext(it)
                    }
                }, {
                    it.printStackTrace()
                })
        }
    }
}