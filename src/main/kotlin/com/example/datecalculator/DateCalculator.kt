package com.example.datecalculator

import com.example.datecalculator.model.Date
import com.example.datecalculator.model.Date.Companion.DAYS_PER_MONTH

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 */
class DateCalculator(
  private val parser: Parser,
  private val validator: Validator,
  private val logger: Logger
) {
  fun diff(start: String, end: String): Int {
    logger.info("Calculating number of full days from $start to $end")

    val dates = listOf(parser.parse(end), parser.parse(start))

    dates.forEach { validator.validate(it) }

    val startDate = dates.minOrNull()!!
    val endDate = dates.maxOrNull()!!

    return if (startDate.year == endDate.year) {
      if (startDate.month == endDate.month) {
        endDate.day - startDate.day - 1
      } else {
        diffDatesWithinSameYearAndDifferentMonths(startDate, endDate)
      }
    } else {
      fullDaysUntilEndOfYear(startDate) +
          daysFromStartOfTheYear(endDate) +
          daysWithinYears(startDate.year + 1, endDate.year - 1)
    }
  }

  private fun diffDatesWithinSameYearAndDifferentMonths(startDate: Date, endDate: Date): Int {
    return fullDaysUntilEndOfMonth(startDate) +
        endDate.fullDaysFromStartOfTheMonth() +
        daysWithinMonths(startDate.month + 1, endDate.month - 1, startDate.year)
  }

  private fun fullDaysUntilEndOfMonth(date: Date): Int {
    return numberOfDaysFor(date.year, date.month) - date.day
  }

  private fun fullDaysUntilEndOfYear(date: Date): Int {
    if(date.month == 12) {
      return 31 - date.day
    }

    val endOfYear = Date(date.year, 12, 31)
    return diffDatesWithinSameYearAndDifferentMonths(date, endOfYear)
  }

  private fun daysFromStartOfTheYear(date: Date): Int {
    if(date.month == 1) {
      return date.day
    }
    val startOfYear = Date(date.year, 1, 1)
    return diffDatesWithinSameYearAndDifferentMonths(startOfYear, date)
  }


  private fun isLeapYear(y: Int): Boolean {
    if(Math.floorMod(y, 400) == 0) {
      return true
    }

    if(Math.floorMod(y, 100) == 0) {
      return false
    }

    if(Math.floorMod(y, 4) == 0) {
      return true
    }

    return false
  }

  private fun numberOfDaysFor(year: Int): Int {
    return if (isLeapYear(year)) {
      366
    } else {
      365
    }
  }

  private fun numberOfDaysFor(year: Int, month: Int): Int {
    if((month == 2) && isLeapYear(year)) {
      return 29
    }

    return DAYS_PER_MONTH[month]
  }

  private fun daysWithinMonths(startMonth: Int, endMonth: Int, year: Int): Int {
    return IntRange(startMonth, endMonth)
      .map { month -> numberOfDaysFor(year, month) }
      .reduce { acc, i -> acc + i }
  }


  private fun daysWithinYears(startYear: Int, endYear: Int): Int {
    return IntRange(startYear, endYear)
      .map { numberOfDaysFor(it) }
      .reduce { acc, i -> acc + i }
  }

  companion object {
    val instance = DateCalculator(DateParser(), DateValidator(), Logger(System.out))
  }

}