package minesweeper

import kotlin.random.Random

fun main() {

    Game.start()

    while (!Game.won()) {
        Game.move()
    }

    Game.congratulations()

}

object Game {

    val scanner = java.util.Scanner(System.`in`)
    val board = Board(9, 9)

    fun start() {
        print("How many mines do you want on the field? ")
        val numberOfMines = scanner.nextInt()
        board.placeMines(numberOfMines)
        board.calcNumbers()
        board.show()
    }

    fun won(): Boolean {

        for (i in 0 until board.rows)
            for (j in 0 until board.columns) {
                if (board.mines[i][j] && !board.marks[i][j] ||
                        board.marks[i][j] && !board.mines[i][j]) {
                    return false
                }
            }

        return true
    }

    fun move() {
        var goodCoordinates = false
        do {
            print("Set/delete mine marks (x and y coordinates): ")
            val column = scanner.nextInt() - 1
            val row = scanner.nextInt() - 1

            if (board.field[row][column].isDigit()) {
                 println("There is a number here!")
            } else {
                board.marks[row][column] = !board.marks[row][column]
                goodCoordinates = true
            }
        } while (!goodCoordinates)
        board.show()
    }

    fun congratulations() {
        println("Congratulations! You found all the mines!")
    }
}

class Board(val rows: Int, val columns: Int) {

    val field = Array(rows) {CharArray(columns) {'.'} }
    val mines = Array(rows) {BooleanArray(columns) {false} }
    val marks = Array(rows) {BooleanArray(columns) {false} }

    fun placeMines(numberOfMines: Int) {
        var i = numberOfMines
        while (i > 0) {
            val row = Random.nextInt(rows)
            val column = Random.nextInt(columns)
            if (!mines[row][column]) {
                mines[row][column] = true
                i--
            }
        }
    }

    fun calcNumbers() {
        for (i in 0 until rows)
            for (j in 0 until columns) {

                if (mines[i][j]) continue

                var sum = 0
                if (i > 0 && mines[i - 1][j]) sum++
                if (i < rows - 1 && mines[i + 1][j]) sum++

                if (j > 0 && mines[i][j - 1]) sum++
                if (j < columns - 1 && mines[i][j + 1]) sum++

                if (i > 0 && j > 0 && mines[i - 1][j - 1]) sum++
                if (i > 0 && j < columns - 1 && mines[i - 1][j + 1]) sum++
                if (i < rows - 1 && j > 0 && mines[i + 1][j - 1]) sum++
                if (i < rows - 1 && j < columns - 1 && mines[i + 1][j + 1]) sum++

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
            print("|")
            for (j in field[i].indices) {
                if (marks[i][j]) {
                    print('*')
                } else {
                    print(field[i][j])
                }
            }
            println("│")
        }
        println("—│—————————│")
    }
}