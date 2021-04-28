package lymiah.ioof.datediff

import java.io.BufferedReader
import java.io.FileReader
import java.io.PrintStream

/**
 * Calculates the absolute difference in days from the start to end date.
 * Also returns the dates sorted chronologically.
 * Validates the given dates.
 */
fun dateDiff(start: Date, end: Date): DateDiffResult {
    if (start > end) return dateDiff(end, start)

    // TODO: Check Dates

    // TODO: Calculate difference

    return DateDiffResult(start, end, 0)
}

/**
 * Matches the expected input format:
 * DD MM YYYY, DD MM YYYY
 * Groups: day1, month1, year1, day2, month2, year2
 */
val INPUT_REGEX =
    """^(?<day1>\d\d) (?<month1>\d\d) (?<year1>\d\d\d\d), (?<day2>\d\d) (?<month2>\d\d) (?<year2>\d\d\d\d)$""".toRegex()

/**
 * Parse group of the given name as a positive number with optional leading zeros.
 */
private fun MatchResult.parseNum(name: String): Int {
    val group = this.groups[name] ?: throw InvalidInputException("Group $name not present")
    return group.value.trimStart('0').toIntOrNull() ?: throw InvalidInputException("Cannot parse ${group.value}")
}

private fun processLine(line: String, out: PrintStream) {
    val match = INPUT_REGEX.matchEntire(line)
        ?: throw InvalidInputException("'$line' is not in the expected format 'DD MM YYYY, DD MM YYYY'")
    val result = dateDiff(
        Date(match.parseNum("year1"), match.parseNum("month1"), match.parseNum("day1")),
        Date(match.parseNum("year2"), match.parseNum("month2"), match.parseNum("day2"))
    )
    // TODO: print out results
}

fun processInput(input: BufferedReader, out: PrintStream) {
    while (true) {
        val line = input.readLine() ?: return
        processLine(line, out)
    }
}

fun main(args: Array<String>) {
    // Take input from stdin if not file is specified, or take from file if one is specified.
    val input: BufferedReader = when (args.size) {
        0 -> System.`in`.bufferedReader()
        1 -> FileReader(args[0]).buffered()
        else -> {
            System.err.println("Expected no input (use stdin) or a single file as input.")
            return
        }
    }
    try {
        input.use {
            processInput(input, System.out)
        }
    } catch (ex: InvalidInputException) {
        System.err.println(ex.message)
    }
}

class InvalidInputException(message: String) : IllegalArgumentException(message)

/**
 * Holds a year, month and day.
 * Does not check for validity.
 * [toString] uses the format 'DD MM YYYY'
 */
data class Date(val year: Int, val month: Int, val day: Int) : Comparable<Date> {
    override fun compareTo(other: Date): Int {
        return COMPARATOR.compare(this, other)
    }

    override fun toString(): String {
        val paddedYear = year.toString().padStart(4, '0')
        val paddedMonth = month.toString().padStart(2, '0')
        val paddedDay = day.toString().padStart(2, '0')
        return "$paddedDay $paddedMonth $paddedYear"
    }

    companion object {
        private val COMPARATOR = compareBy<Date>({ it.year }, { it.month }, { it.day })
    }
}

/**
 * Holds two days and the difference between them.
 * [toString] uses the format 'DD MM YYYY, DD MM YYYY, DIFF'
 */
data class DateDiffResult(val start: Date, val end: Date, val diff: Int) {
    override fun toString(): String {
        return "$start, $end, $diff"
    }
}
