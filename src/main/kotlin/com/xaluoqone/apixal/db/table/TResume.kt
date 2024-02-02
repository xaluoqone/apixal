package com.xaluoqone.apixal.db.table

import com.xaluoqone.apixal.exts.longId

data class TResume(
    val id: Long = longId(),
    val name: String,
    val url: String,
)