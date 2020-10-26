package com.example.datecalculator

import com.example.datecalculator.exception.ParseException
import java.util.regex.Pattern

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 */

interface Parser {
  fun parse(string: String): Date
}

class DateParser: Parser {
  override fun parse(string: String): Date {
    if(!DATE_PATTERN.matcher(string).matches()) {
      throw ParseException("Invalid date : '$string'")
    }

    val attrs = string.split("-")
    return Date(attrs[0].toInt(), attrs[1].toInt(), attrs[2].toInt())
  }

  companion object {
    private const val VALID_YEAR = "((19|2[0-9])[0-9]{2})"

    private val DATE_PATTERN = Pattern.compile(
      "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$"
          + "|^($VALID_YEAR-02-(0[1-9]|1[0-9]|2[0-8]))$"
          + "|^($VALID_YEAR-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
          + "|^($VALID_YEAR-(0[469]|11)-(0[1-9]|[12][0-9]|30))$"
    )
  }
}