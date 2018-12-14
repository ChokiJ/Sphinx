package xyz.choki.SpxServer

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.io.InputStreamReader
import java.io.BufferedReader
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource


@RestController
public class PuzzleController {
    @GetMapping("/puzzles")
    fun getPuzzles(id : Int) : String
    {
        val resource = FileSystemResource("./puzzles/puzzle$id.json")
        val br = BufferedReader(InputStreamReader(resource.getInputStream()))
        return br.readText()
    }

}