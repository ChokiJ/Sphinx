package xyz.choki.SpxServer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
class SpxServerApplication
fun main(args: Array<String>) {
	runApplication<SpxServerApplication>(*args)
}
