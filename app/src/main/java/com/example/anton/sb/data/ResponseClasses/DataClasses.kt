package com.example.anton.sb.data.ResponseClasses


data class ResultAd(
    val id: Long,
    var title: String,
    var price: Int,
    val country: String,
    var city: String,
    val subway_station: String,
    var images_url: List<String>,
    val owner_ad: ResultUser,
    val description_ad: String,
    val time_cre: String
)

data class ResultUser(
    val session_id: String,
    val id: Long,
    val first_name: String,
    val last_name: String,
    val email: String,
    val tel_number: String,
    val about: String,
    val reg_time: String,
    val message: String,
    val error: String
)