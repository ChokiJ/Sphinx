package xyz.choki.SpxServer

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
public class HelloController {
    @GetMapping("/hello/{name}")
    fun hello(@PathVariable("name") name: String):String {
        return "hello, $name!"
    }
    @GetMapping("")
    fun default() : String {
        return "Sphinx Server Version 1"
    }

}