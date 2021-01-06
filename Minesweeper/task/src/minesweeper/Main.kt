package minesweeper

import com.sun.org.apache.xpath.internal.operations.Bool
import kotlin.random.Random

fun main() {

    Game.start()

    while (!Game.won()) {
        val blown = Game.move()
        if (blown) {
            Game.lose()
            return
        }
    }

    Game.congratulations()
}

object Game {

    val scanner = java.util.Scanner(System.`in`)
    val board = Board(9, 9)
    var firstExplored = true

    fun start() {
        print("How many mines do you want on the field? ")
        val numberOfMines = scanner.nextInt()
        board.placeMines(numberOfMines)
        board.calcNumbers() // also recalc at first explore
        board.show()
    }

    fun won(): Boolean {

        var allMinesMarked = true
        var allEmptyOpen = true

        for (i in 0 until board.rows)
            for (j in 0 until board.columns) {
                if (board.mines[i][j] && !board.marks[i][j] ||
                        board.marks[i][j] && !board.mines[i][j]) {
                    allMinesMarked = false
                }
                if (!board.open[i][j] && !board.mines[i][j]) {
                    allEmptyOpen = false
                }
            }

        return allMinesMarked || allEmptyOpen
    }

    fun move(): Boolean {

        print("Set/unset mine marks or claim a cell as free: ")

        val column = scanner.nextInt() - 1
        val row = scanner.nextInt() - 1
        val command = scanner.next()

        when (command) {
            "free" -> {
                if (firstExplored && board.mines[row][column]) {
                    board.placeMines(1)
                    board.mines[row][column] = false
                    board.calcNumbers() // need to recalc
                    board.openRecursively(row, column)
                } else if (board.mines[row][column]) return true
                else board.openRecursively(row, column)
                firstExplored = false
            }
            "mine" -> if (!board.open[row][column]) board.marks[row][column] = !board.marks[row][column]
        }

        board.show()

        return false
    }

    fun lose() {
        board.show(true)
        println("You stepped on a mine and failed!")
    }

    fun congratulations() {
        println("Congratulations! You found all the mines!")
    }
}

class Board(val rows: Int, val columns: Int) {

    val numbers = Array(rows) {IntArray(columns) {0} }
    val mines = Array(rows) {BooleanArray(columns) {false} }
    val marks = Array(rows) {BooleanArray(columns) {false} }
    val open = Array(rows) {BooleanArray(columns) {false} }

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

                numbers[i][j] = sum
            }
    }

    fun show(withMines: Boolean = false) {
        println(" │123456789│")
        println("—│—————————│")
        for (i in 0 until rows) {
            print(i + 1)
            print("|")
            for (j in 0 until columns) {
                when {
                    withMines && mines[i][j] -> print('X')
                    marks[i][j] -> print('*')
                    !open[i][j] -> print('.')
                    numbers[i][j] == 0 -> print('/')
                    else -> print(numbers[i][j])
                }
            }
            println("│")
        }
        println("—│—————————│")
    }

    fun openRecursively(row: Int, column: Int) {
        if (row in 0 until rows && column in 0 until columns) {
            if (open[row][column]) return
            else {
                open[row][column] = true
                marks[row][column] = false
            }

            if (numbers[row][column] == 0) {
                openRecursively(row - 1, column - 1)
                openRecursively(row - 1, column)
                openRecursively(row - 1, column + 1)
                openRecursively(row, column - 1)
                openRecursively(row, column + 1)
                openRecursively(row + 1, column - 1)
                openRecursively(row + 1, column)
                openRecursively(row + 1, column + 1)
            }
        }
    }
}