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
  fun calculateFullDaysBetween(start: String, end: String): Int {
    logger.info("Calculating number of full days from $start to $end")

    val dates = listOf(parser.parse(end), parser.parse(start))

    dates.forEach { validator.validate(it) }

    val startDate = dates.minOrNull()!!
    val endDate = dates.maxOrNull()!!

    return if (startDate.year == endDate.year) {
      if (startDate.month == endDate.month) {
        endDate.day - startDate.day - 1
      } else {
        calculateFullDaysWithinSameYearAndDifferentMonths(startDate, endDate)
      }
    } else {
      calculateFullDaysUntilEndOfYearFor(startDate) +
          calculateFullDaysFromStartOfTheYearFor(endDate) +
          calculateFullDaysInYears(startDate.year + 1, endDate.year - 1)
    }
  }

  private fun calculateFullDaysWithinSameYearAndDifferentMonths(startDate: Date, endDate: Date): Int {
    return calculateFullDaysUntilEndOfMonthFor(startDate) +
        endDate.fullDaysFromStartOfTheMonth() +
        calculateFullDaysInMonths(startDate.month + 1, endDate.month - 1, startDate.year)
  }

  private fun calculateFullDaysUntilEndOfMonthFor(date: Date): Int {
    return numberOfDaysIn(date.year, date.month) - date.day
  }

  private fun calculateFullDaysUntilEndOfYearFor(date: Date): Int {
    if(date.month == 12) {
      return 31 - date.day
    }

    val endOfYear = Date(date.year, 12, 31)
    return calculateFullDaysWithinSameYearAndDifferentMonths(date, endOfYear)
  }

  private fun calculateFullDaysFromStartOfTheYearFor(date: Date): Int {
    if(date.month == 1) {
      return date.day
    }
    val startOfYear = Date(date.year, 1, 1)
    return calculateFullDaysWithinSameYearAndDifferentMonths(startOfYear, date)
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

  private fun numberOfDaysIn(year: Int): Int {
    return if (isLeapYear(year)) {
      366
    } else {
      365
    }
  }

  private fun numberOfDaysIn(year: Int, month: Int): Int {
    if((month == 2) && isLeapYear(year)) {
      return 29
    }

    return DAYS_PER_MONTH[month]
  }

  private fun calculateFullDaysInMonths(startMonth: Int, endMonth: Int, year: Int): Int {
    return IntRange(startMonth, endMonth)
      .map { month -> numberOfDaysIn(year, month) }
      .reduce { acc, i -> acc + i }
  }


  private fun calculateFullDaysInYears(startYear: Int, endYear: Int): Int {
    return IntRange(startYear, endYear)
      .map { numberOfDaysIn(it) }
      .reduce { acc, i -> acc + i }
  }

  companion object {
    val instance = DateCalculator(DateParser(), DateValidator(), Logger(System.out))
  }

}