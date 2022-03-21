package com.plygrnd.ktor.kfiles.entity

import kotlinx.serialization.Serializer

@kotlinx.serialization.Serializable
data class User(
    var no: Int = 0,
    var name: String = "",
    var address: String = ""
)
