package com.example.smartagriculture.bean

data class Notice(
    val code: String,
    val `data`: Data2,
    val msg: String,
    val status: String,
    val success: Boolean
)

data class Data2(
    val number: Int
)