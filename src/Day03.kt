fun main() {

    fun maxJoltageForBank(line: String, k: Int): Long {
        val n = line.length
        var toRemove = n - k
        val stack = StringBuilder()

        for (ch in line) {
            while (toRemove > 0 && stack.isNotEmpty() && stack[stack.length - 1] < ch) {
                stack.setLength(stack.length - 1)
                toRemove--
            }
            stack.append(ch)
        }

        if (toRemove > 0) {
            stack.setLength(stack.length - toRemove)
        }

        val resultStr = if (stack.length > k) stack.substring(0, k) else stack.toString()
        return resultStr.toLong()
    }


    fun part1(input: List<String>): Long {
        var result = 0L
        input.forEach { row ->
            result += maxJoltageForBank(row, 2)
        }
        return result
    }

    fun part2(input: List<String>): Long {
        var result = 0L
        input.forEach { row ->
            result += maxJoltageForBank(row, 12)
        }
        return result
    }

    val testInput = listOf(
        "987654321111111",
        "811111111111119",
        "234234234234278",
        "818181911112111",
    )

    check(part1(testInput) == 357L)
    check(part2(testInput) == 3121910778619L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
