package com.example.anton.sb.data

data class Ad(

    val id: Long,
    val title: String,
    val price: Int,
    val country: String,
    val city: String,
    val subway_station: String,
    val images_url: List<String>,
    val agent_info: User,
    val description: String,
    val time_cre: String
)

data class User(
    val token: String,
    val id: Long,
    val first_name: String,
    val last_name: String,
    val email: String,
    val tel_number: String,
    val about_me: String,
    val time_reg: String
)

data class ResultAdd (
    val id: Long,
                   val title: String,
                   val price: Int,
                   val country: String,
                   val city: String,
                   val subway_station: String,
                   val images_url: List<String>,
                   val agent_info: User,
                   val description: String,
                   val time_cre: String
)

data class ResultUser(
    val token: String,
    val id: Long,
    val first_name: String,
    val last_name: String,
    val email: String,
    val tel_number: String,
    val about_me: String,
    val time_reg: String
)
