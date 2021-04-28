package lymiah.ioof.datediff

import lymiah.ioof.datediff.TestConstants.DAYS_IN_MONTHS
import lymiah.ioof.datediff.TestConstants.DAYS_IN_MONTHS_LEAP_YEAR
import lymiah.ioof.datediff.TestConstants.MONTHS
import lymiah.ioof.datediff.TestConstants.VALID_DATE
import lymiah.ioof.datediff.TestConstants.YEARS
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TestInvalid {

    private fun assertDateInvalid(message: String, date: Date) {
        assertThrows<InvalidInputException>(message) { dateDiff(date, VALID_DATE) }
        assertThrows<InvalidInputException>(message) { dateDiff(VALID_DATE, date) }
    }

    @Test
    fun `Invalid years should throw InvalidInputException`() {
        listOf(
            Date(YEARS.first - 1, 1, 1),
            Date(YEARS.last + 1, 1, 1),
        ).forEach {
            assertDateInvalid("Should be an invalid year", it)
        }
    }

    @Test
    fun `Invalid months should throw InvalidInputException`() {
        sequenceOf(
            Date(2000, MONTHS.first - 1, 1),
            Date(2000, MONTHS.last + 1, 1),
        ).forEach {
            assertDateInvalid("Should be an invalid month", it)
        }
    }

    @Test
    fun `Invalid days should throw InvalidInputException`() {
        DAYS_IN_MONTHS_LEAP_YEAR.flatMapIndexed { idx, range ->
            sequenceOf(
                Date(2000, idx + 1, range.first - 1),
                Date(2000, idx + 1, range.last + 1),
            )
        }.forEach {
            assertDateInvalid("Should be an invalid day", it)
        }
        DAYS_IN_MONTHS.flatMapIndexed { idx, range ->
            sequenceOf(
                Date(2001, idx + 1, range.first - 1),
                Date(2001, idx + 1, range.last + 1),
            )
        }.forEach {
            assertDateInvalid("Should be an invalid day", it)
        }
    }
}
