package xyz.choki.SpxServer

data class Puzzle(
    val info: Info,
    val relation: Relation,
    val solution: Solution,
    val version: Int
)

data class Solution(
    val latitude: Double,
    val longitude: Double
)

data class Relation(
    val branch: Int,
    val next: Int,
    val previous: Int
)

data class Info(
    val `class`: String,
    val description: String,
    val hint: String,
    val id: Int,
    val titile: String,
    val type: Int
)