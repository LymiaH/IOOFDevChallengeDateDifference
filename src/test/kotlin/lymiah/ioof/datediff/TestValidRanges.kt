package lymiah.ioof.datediff

import lymiah.ioof.datediff.TestConstants.DAYS_IN_LEAP_YEAR
import lymiah.ioof.datediff.TestConstants.DAYS_IN_MONTHS
import lymiah.ioof.datediff.TestConstants.DAYS_IN_MONTHS_LEAP_YEAR
import lymiah.ioof.datediff.TestConstants.DAYS_IN_YEAR
import lymiah.ioof.datediff.TestConstants.LEAP_YEAR
import lymiah.ioof.datediff.TestConstants.LEAP_YEARS
import lymiah.ioof.datediff.TestConstants.NORMAL_YEAR
import lymiah.ioof.datediff.TestConstants.NORMAL_YEARS
import org.junit.jupiter.api.Test

class TestValidRanges {
    @Test
    fun `Normal years should have 365 days`() {
        NORMAL_YEARS.forEach {
            val start = Date(it, 1, 1)
            val end = Date(it, 12, 31)
            val result = dateDiff(start, end)
            assert(result.diff == DAYS_IN_YEAR) { "Expected normal year $it to have $DAYS_IN_YEAR but got ${result.diff}" }
        }
    }

    @Test
    fun `Leap years should have 366 days`() {
        LEAP_YEARS.forEach {
            val start = Date(it, 1, 1)
            val end = Date(it, 12, 31)
            val result = dateDiff(start, end)
            assert(result.diff == DAYS_IN_LEAP_YEAR) { "Expected leap year $it to have $DAYS_IN_LEAP_YEAR but got ${result.diff}" }
        }
    }

    @Test
    fun `Months in a normal year have the correct number of days`() {
        DAYS_IN_MONTHS.forEachIndexed { idx, days ->
            val month = idx + 1
            val start = Date(NORMAL_YEAR, month, 1)
            val endTemp = Date(NORMAL_YEAR, month + 1, 1)
            // For if the next month is in the next year
            val end = if (endTemp.month > 12) Date(NORMAL_YEAR + 1, 1, 1) else endTemp
            val result = dateDiff(start, end)
            val expectedNumDays = days.last - days.first + 1
            assert(result.diff == expectedNumDays) { "Expected month $month to have $expectedNumDays but got ${result.diff}" }
        }
    }

    @Test
    fun `Months in a leap year are correct`() {
        DAYS_IN_MONTHS_LEAP_YEAR.forEachIndexed { idx, days ->
            val month = idx + 1
            val start = Date(LEAP_YEAR, month, 1)
            val endTemp = Date(LEAP_YEAR, month + 1, 1)
            // For if the next month is in the next year
            val end = if (endTemp.month > 12) Date(LEAP_YEAR + 1, 1, 1) else endTemp
            val result = dateDiff(start, end)
            val expectedNumDays = days.last - days.first + 1
            assert(result.diff == expectedNumDays) { "Expected month $month to have $expectedNumDays but got ${result.diff}" }
        }
    }
}
