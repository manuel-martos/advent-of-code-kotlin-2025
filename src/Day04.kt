fun main() {
    data class Map(
        val totalCols: Int,
        val totalRows: Int,
        val content: List<CharArray>,
    ) {
        fun countAdjacent(row: Int, col: Int, value: Char): Int {
            val minRow = (row - 1).coerceAtLeast(0)
            val minCol = (col - 1).coerceAtLeast(0)
            val maxRow = (row + 1).coerceAtMost(totalRows - 1)
            val maxCol = (col + 1).coerceAtMost(totalCols - 1)
            var result = 0
            for (curRow in minRow..maxRow) {
                for (curCol in minCol..maxCol) {
                    if (curRow == row && curCol == col) {
                        continue
                    }
                    if (content[curRow][curCol] == value) {
                        result++
                    }
                }
            }
            return result
        }

        fun updateCell(row: Int, col: Int, value: Char) {
            content[row][col] = value
        }
    }

    fun parse(input: List<String>): Map {
        val width = input.first().length
        val height = input.size
        return Map(
            totalCols = width,
            totalRows = height,
            content = input.map { it.toCharArray() }
        )
    }

    fun part1(map: Map): Int {
        var result = 0
        for (curRow in 0 until map.totalRows) {
            for (curCol in 0 until map.totalCols) {
                if (map.content[curRow][curCol] == '@' && map.countAdjacent(curRow, curCol, '@') < 4) {
                    result++
                }
            }
        }
        return result
    }

    fun part2(map: Map): Int {
        var result = 0
        var lastRemoval: Int
        do {
            val removedCells = mutableListOf<Pair<Int, Int>>()
            lastRemoval = 0
            for (curRow in 0 until map.totalRows) {
                for (curCol in 0 until map.totalCols) {
                    if (map.content[curRow][curCol] == '@' && map.countAdjacent(curRow, curCol, '@') < 4) {
                        lastRemoval++
                        removedCells.add(curRow to curCol)
                    }
                }
            }
            removedCells.forEach {
                map.updateCell(it.first, it.second, '.')
            }
            result += lastRemoval
        } while (lastRemoval != 0)
        return result
    }

    // Test if implementation meets criteria from the description, like:
    val testInput = parse(
        listOf(
            "..@@.@@@@.",
            "@@@.@.@.@@",
            "@@@@@.@.@@",
            "@.@@@@..@.",
            "@@.@@@@.@@",
            ".@@@@@@@.@",
            ".@.@.@.@@@",
            "@.@@@.@@@@",
            ".@@@@@@@@.",
            "@.@.@@@.@.",
        )
    )
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(parse(input)).println()
    part2(parse(input)).println()
}
