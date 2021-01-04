package minesweeper

import kotlin.random.Random

fun main() {

    Game.start()

    // Game.won()
    // Game.move()
    // Game.congratulations()

}

object Game {

    val board = Board(9, 9)

    fun start() {
        print("How many mines do you want on the field? ")
        val mines = readLine()!!.toInt()
        board.placeMines(mines)
        board.calcNumbers()
        board.show()
    }
}

class Board(val rows: Int, val columns: Int) {

    val field = Array(rows) {CharArray(columns) {'.'} }

    fun placeMines(mines: Int) {
        var i = mines
        while (i > 0) {
            val row = Random.nextInt(rows)
            val column = Random.nextInt(columns)
            if (field[row][column] == '.') {
                field[row][column] = 'X'
                i--
            }
        }
    }

    fun calcNumbers() {
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
    }

    fun show() {
        println(" │123456789│")
        println("—│—————————│")
        for (i in field.indices) {
            print(i + 1)
            println(field[i].joinToString("", "│", "│"))
        }
        println("—│—————————│")
    }
}