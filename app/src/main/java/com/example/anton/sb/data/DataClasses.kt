package com.example.anton.sb.data

/**
 * @author Anton Kirichenkov
 */

/**
 * Represents a ResultAd, with the given [id], [title], [price], [city], [ad_images], [owner_ad], [description_ad].
 */
data class ResultAd(
    /**
     * id of ad
     */
    val id: Long,
    /**
     * title of ad
     */
    var title: String,
    /**
     * price of ad
     */
    var price: Int,
    /**
     * city of ad
     */
    var city: String,
    /**
     * images url for ad
     */
    var ad_images: ArrayList<String>,
    /**
     * information about user-owner ad
     */
    val owner_ad: ResultUser,
    /**
     * description of ad
     */
    val description_ad: String
) {
    fun copy(ad: ResultAd) {
        this.copy(ad)
    }

    constructor(ad: ResultAd) : this(
        ad.id,
        ad.title,
        ad.price,
        ad.city,
        ad.ad_images,
        ad.owner_ad,
        ad.description_ad
    )
}

/**
 * Represents a ResultUser, with the given [session_id], [id], [first_name], [last_name], [email], [tel_number], [about].
 */
data class ResultUser(
    /**
     * session_id of user session
     */
    val session_id: String,
    /**
     * user id
     */
    val id: Long,
    /**
     * fist name of user
     */
    val first_name: String,
    /**
     * last name of user
     */
    val last_name: String,
    /**
     * email of user
     */
    val email: String,
    /**
     * phone number of user
     */
    val tel_number: String,
    /**
     * information about user
     */
    val about: String
)