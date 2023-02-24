package com.sube.reggie

import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.transaction.annotation.EnableTransactionManagement

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
@ServletComponentScan
class ReggieApplication

fun main(args: Array<String>) {

    runApplication<ReggieApplication>(*args)
}
