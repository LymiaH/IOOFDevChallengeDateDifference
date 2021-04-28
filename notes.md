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

## Leap Years

Most years are of length 365 days with leap years being 366 days.
Leap years are defined as years divisible by 4, and if also divisible by 100,
then it also needs to be divisible by 400 as well.
Within the limits, the only special year is 1900, which is not a leap year.
