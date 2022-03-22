package com.plygrnd.ktor.kfiles.entity

@kotlinx.serialization.Serializable
data class User(
    var no: Int = 0,
    var name: String = "",
    var address: String = ""
)
