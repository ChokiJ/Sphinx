package xyz.choki.SpxServer

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.apache.tomcat.jni.SSL.setPassword
import java.io.FileNotFoundException
import org.springframework.util.ResourceUtils
import java.net.URL

@RestController
public class PuzzleController {
    @GetMapping("/puzzles")
    fun getPuzzles(id : Int) : String
    {
        val file = ResourceUtils.getFile("classpath:puzzles/puzzle$id.json")
        return file.readText()
    }

}