package com.example.datecalculator

import com.example.datecalculator.exception.ValidationException

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 */

interface Validator {
  fun validate(date: Date)
}

class DateValidator: Validator {
  override fun validate(date: Date) {
    if(date.year < MIN_YEAR) {
      throw ValidationException("Date cannot be before $MIN_YEAR")
    }

    if(date.year > MAX_YEAR) {
      throw ValidationException("Date cannot be after $MAX_YEAR")
    }
  }

  companion object {
    const val MIN_YEAR = 1901
    const val MAX_YEAR = 2999
  }
}