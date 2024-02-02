package com.xaluoqone.apixal.exts

import cn.hutool.core.util.IdUtil

fun longId(): Long {
    return IdUtil.getSnowflakeNextId()
}