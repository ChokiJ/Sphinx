package xyz.choki.SpxServer

import com.google.gson.Gson
import org.slf4j.LoggerFactory
import org.springframework.boot.logging.logback.LogbackLoggingSystem
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.io.InputStreamReader
import java.io.BufferedReader
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import java.util.logging.Level
import java.util.logging.Logger


@RestController
public class PuzzleController {
    val logger = LoggerFactory.getLogger(PuzzleController::class.java)
    @GetMapping("/puzzles")
    fun getPuzzles(id : Int) : String
    {
        logger.info("谜题$id 资料获取")
        val resource = FileSystemResource("./puzzles/puzzle$id.json")
        val br = BufferedReader(InputStreamReader(resource.getInputStream()))
        return br.readText()
    }
    //解决谜题
    @GetMapping("/solve")
    fun solvePuzzles(id : Int,longitude : Double,latitude : Double) : Boolean
    {
        logger.info("谜题$id 解决请求 经度$longitude 纬度$latitude")
        val resource = FileSystemResource("./puzzles/puzzle$id.json")
        val br = BufferedReader(InputStreamReader(resource.getInputStream()))
        val gson = Gson()
        val pzl = gson.fromJson(br.readText(), Puzzle::class.javaObjectType)
        //判断经纬度是否在一范围内
        if ((pzl.solution.longitude - 0.00003 <= longitude) && (longitude <= pzl.solution.longitude + 0.00003))
        {
            if ((pzl.solution.latitude - 0.00003 <= latitude) && (latitude <= pzl.solution.latitude + 0.00003))
            {
                logger.info("谜题$id 解决成功")
                return true
            }
        }
        logger.info("谜题$id 解决失败")
        return false
    }

}