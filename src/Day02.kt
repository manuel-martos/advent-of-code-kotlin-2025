import kotlin.math.ceil
import kotlin.math.log10

fun main() {

    fun parse(input: List<String>): List<LongRange> =
        input
            .first()
            .split(",")
            .map {
                val values = it.split("-")
                values.first().toLong()..values.last().toLong()
            }

    fun part1(input: List<LongRange>): Long {
        var result = 0L
        input.forEach {
            for (cur in it) {
                val length = ceil(log10(cur.toDouble())).toInt()
                if (length % 2 == 0) {
                    val text = cur.toString()
                    val split1 = text.take(length / 2)
                    val split2 = text.substring(length / 2)
                    if (split1 == split2) {
                        result += cur
                    }
                }
            }
        }
        return result
    }

    fun part2(input: List<LongRange>): Long {
        var result = 0L
        input.forEach {
            for (cur in it) {
                val text = cur.toString()
                val textLength = text.length
                val isInvalid = (1..textLength / 2)
                    .any { curLength ->
                        val slice = text.take(curLength)
                        textLength % curLength == 0 &&
                                text.windowed(curLength, curLength, false).all { it == slice }
                    }
                if (isInvalid) {
                    result += cur
                }
            }
        }
        return result
    }

    // Test if implementation meets criteria from the description, like:
    val testInput =
        parse(listOf("11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"))
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    // Read the input from the `src/Day01.txt` file.
    val input = parse(readInput("Day02"))
    part1(input).println()
    part2(input).println()
}
