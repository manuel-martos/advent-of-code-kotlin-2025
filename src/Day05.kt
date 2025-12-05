fun main() {
    data class Inventory(
        val ranges: List<LongRange>,
        val ingredients: List<Long>,
    )

    fun parse(input: List<String>): Inventory {
        val ranges: MutableList<LongRange> = mutableListOf()
        val ingredients: MutableList<Long> = mutableListOf()
        var parsingRanges = true
        for (line in input) {
            if (line.isEmpty()) {
                parsingRanges = false
                continue
            }

            if (parsingRanges) {
                val (first, last) = line.split('-')
                ranges.add(first.toLong()..last.toLong())
            } else {
                ingredients.add(line.toLong())
            }
        }
        return Inventory(
            ranges = ranges,
            ingredients = ingredients,
        )
    }

    fun part1(inventory: Inventory): Int {
        var result = 0
        for (ingredient in inventory.ingredients) {
            val isFresh = inventory.ranges.any { it.contains(ingredient) }
            if (isFresh) {
                result++
            }
        }
        return result
    }

    fun part2(inventory: Inventory): Long {
        val sortedRanges = inventory.ranges.sortedBy { it.first }
        val mergedRanges = mutableListOf<LongRange>()
        var currentMerge = sortedRanges.first()

        for (i in 1 until sortedRanges.size) {
            val nextRange = sortedRanges[i]
            if (nextRange.first <= currentMerge.last + 1) {
                currentMerge = currentMerge.first..maxOf(currentMerge.last, nextRange.last)
            } else {
                mergedRanges.add(currentMerge)
                currentMerge = nextRange
            }
        }
        mergedRanges.add(currentMerge)

        return mergedRanges.sumOf {
            it.last - it.first + 1L
        }
    }

    // Test if implementation meets criteria from the description, like:
    val testInput = parse(
        listOf(
            "3-5",
            "10-14",
            "16-20",
            "12-18",
            "",
            "1",
            "5",
            "8",
            "11",
            "17",
            "32",
        )
    )
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(parse(input)).println()
    part2(parse(input)).println()
}
