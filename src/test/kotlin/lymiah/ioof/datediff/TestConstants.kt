package lymiah.ioof.datediff

object TestConstants {
    const val DAYS_IN_YEAR = 365
    const val DAYS_IN_LEAP_YEAR = 366
    val YEARS = 1900..2010
    val LEAP_YEARS = YEARS.filter {
        isLeapYear(it)
    }
    val LEAP_YEAR = 2000
    val NORMAL_YEARS = YEARS.filter {
        !isLeapYear(it)
    }
    val NORMAL_YEAR = 2001
    val MONTHS = 1..12
    val DAYS_IN_MONTHS = MONTHS.map {
        when {
            it == 2 -> 1..28
            it <= 7 -> if (it % 2 == 0) 1..30 else 1..31
            else -> if (it % 2 == 0) 1..31 else 1..30
        }
    }
    val DAYS_IN_MONTHS_LEAP_YEAR = MONTHS.map {
        when {
            it == 2 -> 1..29
            it <= 7 -> if (it % 2 == 0) 1..30 else 1..31
            else -> if (it % 2 == 0) 1..31 else 1..30
        }
    }
    val VALID_DATE = Date(2000, 1, 1)

    private fun isLeapYear(year: Int) = year % 4 == 0 && if (year % 100 == 0) year % 400 == 0 else true
}
