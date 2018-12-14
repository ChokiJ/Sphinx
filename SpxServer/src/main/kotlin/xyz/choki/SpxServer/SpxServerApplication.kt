package xyz.choki.SpxServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpxServerApplication

fun main(args: Array<String>) {
	runApplication<SpxServerApplication>(*args)
}

