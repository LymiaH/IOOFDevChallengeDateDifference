# Notes

## Task

Given two and dates in the format:

```txt
DD MM YYYY, DD MM YYYY
```

Make sure to validate inputs.

Output the earlier date, the later date and difference in days using the format:

```txt
DD MM YYYY, DD MM YYYY, difference
```

## Limits

- No use of date manipulation libraries. (Does this include tests as well?)
- Optional: Limit date calculation to years 1900 to 2010 (inclusive)

## Assumptions

- Gregorian Calender
- Difference output is absolute

## Gregorian Calender

There are 12 months in a year with the following day lengths:

- 01: 31 days, January
- 02: 28 days, February (29 days on leap years)
- 03: 31, March
- 04: 30, April
- 05: 31, May
- 06: 30, June
- 07: 31, July
- 08: 31, August
- 09: 30, September
- 10: 31, October
- 11: 30, November
- 12: 31, December

### Leap Years

Most years are of length 365 days with leap years being 366 days.
Leap years are defined as years divisible by 4, and if also divisible by 100,
then it also needs to be divisible by 400 as well.
Within the limits, the year 1900 is not a leap year, but the year 2000 is.
