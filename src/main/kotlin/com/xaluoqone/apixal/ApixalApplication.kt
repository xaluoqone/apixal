package com.xaluoqone.apixal

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan("com.xaluoqone.apixal.db.dao")
class ApixalApplication

fun main(args: Array<String>) {
    runApplication<ApixalApplication>(*args)
}