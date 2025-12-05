import kotlin.math.abs

fun main() {

    fun parse(input: List<String>): List<Int> =
        input.map { line ->
            val sign = if (line[0] == 'R') 1 else -1
            line.substring(1).toInt() * sign
        }

    fun wrap(x: Int): Int =
        ((x % 100) + 100) % 100

    fun countHitsOnZero(start: Int, delta: Int): Int {
        val distance = abs(delta).toLong()
        if (distance == 0L) return 0

        val sign = if (delta > 0) 1 else -1
        val firstRaw = if (sign == 1) {
            (100 - start) % 100
        } else {
            start % 100
        }

        var first = firstRaw.toLong()
        if (first == 0L) first = 100L

        return if (first <= distance) {
            (1L + (distance - first) / 100L).toInt()
        } else {
            0
        }
    }

    fun part1(movements: List<Int>): Int {
        var zeroCounter = 0
        movements.fold(50) { cur, delta ->
            val next = wrap(cur + delta)
            if (next == 0) zeroCounter++
            next
        }


        return zeroCounter
    }

    fun part2(movements: List<Int>): Int {
        var zeroCounter = 0

        movements.fold(50) { cur, delta ->
            zeroCounter += countHitsOnZero(cur, delta)
            wrap(cur + delta)
        }

        return zeroCounter
    }

    // Test if implementation meets criteria from the description, like:
    val testInput = parse(
        listOf(
            "L68",
            "L30",
            "R48",
            "L5",
            "R60",
            "L55",
            "L1",
            "L99",
            "R14",
            "L82",
        )
    )
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    val input = parse(readInput("Day01"))
    part1(input).println()
    part2(input).println()
}
