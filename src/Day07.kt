import java.util.*

fun main() {
    data class LaboratoryMap(
        val start: Pair<Int, Int>,
        val map: List<String>,
    )

    fun parse(input: List<String>): LaboratoryMap {
        val row = input.indices.find { input[it].contains("S") }!!
        val col = input[row].indexOf("S")
        return LaboratoryMap(
            start = row to col,
            map = input,
        )
    }

    fun part1(laboratoryMap: LaboratoryMap): Int {
        val map = laboratoryMap.map
        var result = 0
        var currentBeams = mutableSetOf(laboratoryMap.start)
        repeat((laboratoryMap.start.first until laboratoryMap.map.size - 1).count()) {
            currentBeams = currentBeams.flatMap { beamPosition ->
                val nextCell = map[beamPosition.first + 1][beamPosition.second]
                when (nextCell) {
                    '^' -> {
                        result++
                        setOf(
                            beamPosition.copy(first = beamPosition.first + 1, second = beamPosition.second - 1),
                            beamPosition.copy(first = beamPosition.first + 1, second = beamPosition.second + 1),
                        )
                    }

                    else -> setOf(beamPosition.copy(first = beamPosition.first + 1))
                }
            }.toMutableSet()
        }
        return result
    }

    fun part2(laboratoryMap: LaboratoryMap): Long {
        val height = laboratoryMap.map.size
        val width = laboratoryMap.map[0].length

        val dpCurrent = LongArray(width)
        val dpNext = LongArray(width)

        val startRow = laboratoryMap.start.first
        val startCol = laboratoryMap.start.second

        dpCurrent[startCol] = 1L

        for (currentRow in startRow until height - 1) {
            Arrays.fill(dpNext, 0L)

            for (currentCol in 0 until width) {
                val ways = dpCurrent[currentCol]
                if (ways == 0L) continue

                val nextCell = laboratoryMap.map[currentRow + 1][currentCol]
                when (nextCell) {
                    '^' -> {
                        dpNext[currentCol - 1] += ways
                        dpNext[currentCol + 1] += ways
                    }

                    else -> dpNext[currentCol] += ways
                }
            }

            for (c in 0 until width) {
                dpCurrent[c] = dpNext[c]
            }
        }

        return dpCurrent.sum()
    }

    // Test if implementation meets criteria from the description, like:
    val testInput = parse(
        listOf(
            ".......S.......",
            "...............",
            ".......^.......",
            "...............",
            "......^.^......",
            "...............",
            ".....^.^.^.....",
            "...............",
            "....^.^...^....",
            "...............",
            "...^.^...^.^...",
            "...............",
            "..^...^.....^..",
            "...............",
            ".^.^.^.^.^...^.",
            "...............",
        )
    )
    check(part1(testInput) == 21)
    check(part2(testInput) == 40L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputNoTrim("Day07")
    part1(parse(input)).println()
    part2(parse(input)).println()
}
