package com.example.anton.sb.extensions

import android.content.Context
import android.util.Log
import com.example.anton.sb.data.ResultAd
import com.example.anton.sb.service.ApiService
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast

/**
 * @author Anton Kirichenkov
 */
/**
 *  This method uses for update items of recyclerView in MainActivity.
 *  Method called [ApiService.getAds] with parameters dataList.size and limit (10).
 *  If result isn't successful, then display error
 *
 *  @param dataList [ArrayList]
 *  @param context
 *
 *  @return [ArrayList]
 *
 *  @see ApiService.getAds
 */
fun updateDataList(dataList: ArrayList<ResultAd>, context: Context): ArrayList<ResultAd> {

    val apiService: ApiService = ApiService.create()
    apiService.getAds(dataList.size, 10)
        .subscribe({ result ->
            dataList.addAll(result)
            if (dataList.isEmpty())
                context.runOnUiThread {
                    context.toast("Объявлений нет")
                }
        }, { error ->
            Log.d("1", "$error")
            context.runOnUiThread {
                context.toast(handleError(error))
            }
        })
    return dataList
}

/**
 *  This method uses for update items of recyclerView in SearchActivity.
 *  Method called [ApiService.getAdsSearch] with parameters string, searchList.size and limit (10).
 *  If result isn't successful, then display error
 *
 *  @param searchList [ArrayList]
 *  @param string search line
 *  @param context
 *
 *  @return [ArrayList]
 *
 *  @see ApiService.getAdsSearch
 */
fun updateSearchList(
    searchList: ArrayList<ResultAd>,
    string: String,
    context: Context
): ArrayList<ResultAd> {

    val apiService: ApiService = ApiService.create()

    apiService.getAdsSearch(string, searchList.size, 10)
        .subscribe({ result ->
            searchList.addAll(result)
            if (searchList.isEmpty())
                context.toast("Объявления не найдены")
        }, { error ->
            Log.d("1", "$error")
            context.toast(handleError(error))
        })
    return searchList
}