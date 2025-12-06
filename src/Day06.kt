fun main() {
    data class MathHomework(
        val values: List<List<String>>,
        val operations: List<Char>,
    )

    fun isBlankColumn(input: List<String>, col: Int): Boolean {
        return input.all { row -> row[col] == ' ' }
    }

    fun parse(input: List<String>): MathHomework {
        val operationRegex = Regex("[+*]")
        val valuesRows = input.take(input.size - 1).map { "$it " }
        val splitColumns = listOf(-1) + valuesRows[0].indices.filter { isBlankColumn(valuesRows, it) }
        return MathHomework(
            values = valuesRows.map { line -> splitColumns.windowed(2) { (colStart, colEnd) -> line.substring(colStart + 1, colEnd) } },
            operations = operationRegex.findAll(input.last()).map { it.value[0] }.toList(),
        )
    }

    fun part1(input: MathHomework): Long {
        var result = 0L
        for (curColumn in 0 until input.values[0].size) {
            val curOperation = input.operations[curColumn]
            val curValues = (0 until input.values.size).map { input.values[it][curColumn].trim().toLong() }
            val curResult = if (curOperation == '+') {
                curValues.sumOf { it }
            } else {
                curValues.fold(1L) { c, n -> c * n }
            }
            result += curResult
        }

        return result
    }

    fun part2(input: MathHomework): Long {
        var result = 0L
        for (curColumn in 0 until input.values[0].size) {
            val curOperation = input.operations[curColumn]
            val curValues = (0 until input.values.size).map { input.values[it][curColumn] }
            val tempValues = curValues[0].indices.map { col -> curValues.indices.map { row -> curValues[row][col] } }
            val effectiveValues = tempValues.map { it.joinToString(separator = "").trim().toLong() }
            val curResult = if (curOperation == '+') {
                effectiveValues.sumOf { it }
            } else {
                effectiveValues.fold(1L) { c, n -> c * n }
            }
            result += curResult
        }
        return result
    }

    // Test if implementation meets criteria from the description, like:
    val testInput = parse(
        listOf(
            "123 328  51 64 ",
            " 45 64  387 23 ",
            "  6 98  215 314",
            "*   +   *   +  ",
        )
    )
    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputNoTrim("Day06")
    part1(parse(input)).println()
    part2(parse(input)).println()
}
