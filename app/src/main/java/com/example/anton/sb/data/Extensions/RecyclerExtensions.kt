package com.example.anton.sb.data.Extensions

import com.example.anton.sb.data.ApiService
import com.example.anton.sb.data.ResponseClasses.ResultAd
import io.reactivex.android.schedulers.AndroidSchedulers


fun updateDataList(dataList: ArrayList<ResultAd>): ArrayList<ResultAd> {

    val apiService: ApiService = ApiService.create()
    apiService.getAds(dataList.size, 10)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ result ->

            dataList.addAll(result)

        }, {}
        )
    return dataList
}

fun updateSearchList(
    searchList: ArrayList<ResultAd>,
    string: String
): ArrayList<ResultAd> {

    val apiService: ApiService = ApiService.create()

    apiService.getAdsSearch(string, searchList.size, 10)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ result ->

            searchList.addAll(result)

        }, {}
        )
    return searchList
}