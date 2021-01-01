package minesweeper

const val rows = 8
const val columns = 10

fun main() {
    val field = arrayOf(
            "..X.......",
            ".........X",
            "X.........",
            "..........",
            "..X....X..",
            "..........",
            ".....X....",
            ".....X....",
    )

    for (i in field) {
        println(i)
    }
}
