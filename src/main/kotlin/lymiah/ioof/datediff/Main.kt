package lymiah.ioof.datediff

import java.io.BufferedReader
import java.io.FileReader
import java.io.PrintStream


/**
 * Checks if the year is a leap year.
 * Leap years are divisible by 4 except if they are also divisible by 100 and not by 400.
 */
private fun isLeapYear(year: Int): Boolean {
    return year % 4 == 0 && if (year % 100 == 0) year % 400 == 0 else true
}

/**
 * Calculates the days in a month.
 */
private fun daysInMonth(year: Int, month: Int): Int = when (month) {
    1 -> 31
    2 -> if (isLeapYear(year)) 29 else 28
    3 -> 31
    4 -> 30
    5 -> 31
    6 -> 30
    7 -> 31
    8 -> 31
    9 -> 30
    10 -> 31
    11 -> 30
    12 -> 31
    else -> throw throw InvalidInputException("Month $month is out of range [1, 12]")
}

/**
 * Calculates the days in a year.
 */
private fun daysInYear(year: Int) = if (isLeapYear(year)) 366 else 365

/**
 * Checks that the year is between 1900 and 2010 inclusive.
 * Checks that the month is between 1 and 12 inclusive.
 * Checks that the day is between 1 and the number of days in the given month.
 * @throws InvalidInputException if any of the above checks fail
 */
private fun checkDate(date: Date) {
    if (date.year < 1900 || date.year > 2010) throw InvalidInputException("Year ${date.year} is out of range [1900, 2010]")
    if (date.month < 1 || date.month > 12) throw InvalidInputException("Month ${date.month} is out of range [1, 12]")
    if (date.day < 1) throw InvalidInputException("Day ${date.day} should be at least 1")
    val days = daysInMonth(date.year, date.month)
    if (date.day < 1 || date.day > days) throw InvalidInputException("Day ${date.day} should be at most $days for ${date.year}/${date.month}")
}

/**
 * Calculates the absolute difference in days from the start to end date.
 * Does not validate dates.
 * Expects the start date to always be earlier or equal to the end date.
 * Performance is O(deltaYear + deltaMonth)
 */
private fun dateDiffInternal(start: Date, end: Date): Int = when (start.year) {
    // When the years are equal
    end.year -> when (start.month) {
        // And the month is equal, we just get the difference between the days
        end.month -> end.day - start.day
        // If just the year is equal
        else -> {
            val monthDiff = (start.month until end.month).map { daysInMonth(start.year, it) }.sum()
            val startMonth = Date(start.year, start.month, 1)
            val startOff = dateDiffInternal(startMonth, start)
            val endMonth = Date(end.year, end.month, 1)
            val endOff = dateDiffInternal(endMonth, end)
            // The difference is the days between the months - start offset + end offset
            // offset is from the beginning of the month to the day of the same month
            monthDiff - startOff + endOff
        }
    }
    // If the year is not equal
    else -> {
        val yearDiff = (start.year until end.year).map { daysInYear(it) }.sum()
        val startYear = Date(start.year, 1, 1)
        val startOff = dateDiffInternal(startYear, start)
        val endYear = Date(end.year, 1, 1)
        val endOff = dateDiffInternal(endYear, end)
        // The difference is the days between the years - start offset + end offset
        // offset is from the beginning of the year to the day and month of the same year
        yearDiff - startOff + endOff
    }
}

/**
 * Calculates the absolute difference in days from the start to end date.
 * Also returns the dates sorted chronologically.
 * Validates the given dates.
 */
fun dateDiff(start: Date, end: Date): DateDiffResult {
    if (start > end) return dateDiff(end, start)

    checkDate(start)
    checkDate(end)

    return DateDiffResult(start, end, dateDiffInternal(start, end))
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
    return group.value.trimStart('0').padStart(1, '0').toIntOrNull()
        ?: throw InvalidInputException("Cannot parse ${group.value}")
}

private fun processLine(line: String, out: PrintStream) {
    val match = INPUT_REGEX.matchEntire(line)
        ?: throw InvalidInputException("'$line' is not in the expected format 'DD MM YYYY, DD MM YYYY'")
    val result = dateDiff(
        Date(match.parseNum("year1"), match.parseNum("month1"), match.parseNum("day1")),
        Date(match.parseNum("year2"), match.parseNum("month2"), match.parseNum("day2"))
    )

    out.println(result)
}

private fun processInput(input: BufferedReader, out: PrintStream) {
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
