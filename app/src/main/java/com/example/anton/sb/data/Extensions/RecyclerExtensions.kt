package com.example.anton.sb.data.Extensions

import android.util.Log
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.data.ResponseClasses.ResultAd
import io.reactivex.android.schedulers.AndroidSchedulers


fun updateDataList(dataList: ArrayList<ResultAd>): ArrayList<ResultAd> {

    val apiService: ApiService = ApiService.create()

    apiService.getAds(dataList.size, 10)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ result ->

            dataList.addAll(result.body()!!)
            Log.d("rec", "$result")
        }, { error ->
            //handleError(error, "Что-то пошло не так")
        })
    return dataList
}

