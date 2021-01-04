package minesweeper

import kotlin.random.Random

const val rows = 9
const val columns = 9

fun main() {
    val field = Array(rows) {CharArray(columns) {'.'} }

    print("How many mines do you want on the field? ")
    val mines = readLine()!!.toInt()

    var i = mines
    while (i > 0) {
        val row = Random.nextInt(rows)
        val column = Random.nextInt(columns)
        if (field[row][column] == '.') {
            field[row][column] = 'X'
            i--
        }
    }

    for (i in 0 until rows)
        for (j in 0 until columns) {

            if (field[i][j] == 'X') continue

            var sum = 0
            if (i > 0 && field[i - 1][j] == 'X') sum++
            if (i < rows - 1 && field[i + 1][j] == 'X') sum++

            if (j > 0 && field[i][j - 1] == 'X') sum++
            if (j < columns - 1 && field[i][j + 1] == 'X') sum++

            if (i > 0 && j > 0 && field[i - 1][j - 1] == 'X') sum++
            if (i > 0 && j < columns - 1 && field[i - 1][j + 1] == 'X') sum++
            if (i < rows - 1 && j > 0 && field[i + 1][j - 1] == 'X') sum++
            if (i < rows - 1 && j < columns - 1 && field[i + 1][j + 1] == 'X') sum++

            if (sum > 0) {
                field[i][j] = sum.toChar() + '0'.toInt()
            }
        }


    for (i in field) {
        println(i)
    }
}
