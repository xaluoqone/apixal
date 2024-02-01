package com.xaluoqone.apixal.db.table

data class TResume(
    val id: Long? = null,
    val name: String,
    val url: String,
)

fun resume(name: String, url: String): TResume {
    return TResume(null, name, url)
}