package com.example.anton.sb.data.ResponseClasses
/**
 * Represents a ResultAd, with the given [id], [title], [price], [city], [images_url], [owner_ad], [description_ad].
 */
data class ResultAd(
    val id: Long,
    var title: String,
    var price: Int,
    var city: String,
    var images_url: ArrayList<String>?,
    val owner_ad: ResultUser,
    val description_ad: String
)

/**
 * Represents a ResultUser, with the given [session_id], [id], [first_name], [last_name], [email], [tel_number], [about].
 */
data class ResultUser(
    val session_id: String,
    val id: Long,
    val first_name: String,
    val last_name: String,
    val email: String,
    val tel_number: String,
    val about: String
)