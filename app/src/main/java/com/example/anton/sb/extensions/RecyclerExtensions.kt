package com.example.anton.sb.extensions

import android.util.Log
import com.example.anton.sb.service.ApiService
import com.example.anton.sb.data.ResultAd

/**
 * @author Anton Kiricenkov
 */
/**
 *  This method uses for update items of recyclerView in MainActivity.
 *  Method called [ApiService.getAds] with parameters dataList.size and limit (10).
 *
 *  @param dataList [ArrayList]
 *
 *  @return [ArrayList]
 *
 *  @see ApiService.getAds
 */
fun updateDataList(dataList: ArrayList<ResultAd>): ArrayList<ResultAd> {

    val apiService: ApiService = ApiService.create()
    apiService.getAds(dataList.size, 10)
        .subscribe({ result ->

            dataList.addAll(result)

            Log.d("Photo", "$result")
        }, {})
    return dataList
}

/**
 *  This method uses for update items of recyclerView in SearchActivity.
 *  Method called [ApiService.getAdsSearch] with parameters string, searchList.size and limit (10).
 *
 *  @param searchList [ArrayList]
 *
 *  @return [ArrayList]
 *
 *  @see ApiService.getAdsSearch
 */
fun updateSearchList(
    searchList: ArrayList<ResultAd>,
    string: String
): ArrayList<ResultAd> {

    val apiService: ApiService = ApiService.create()

    apiService.getAdsSearch(string, searchList.size, 10)
        .subscribe({ result ->

            searchList.addAll(result)
        }, {})
    return searchList
}