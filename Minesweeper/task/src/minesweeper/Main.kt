package minesweeper

import kotlin.random.Random

const val rows = 9
const val columns = 9

fun main() {
    val field = arrayOf(
            ".........",
            ".........",
            ".........",
            ".........",
            ".........",
            ".........",
            ".........",
            ".........",
            ".........",
    )

    print("How many mines do you want on the field? ")
    val mines = readLine()!!.toInt()

    var i = mines
    while (i > 0) {
        val row = Random.nextInt(rows)
        val column = Random.nextInt(columns)
        if (field[row][column] == '.') {
            val chars = field[row].toCharArray()
            chars[column] = 'X'
            field[row] = String(chars)
            i--
        }
    }

    for (i in field) {
        println(i)
    }
}
