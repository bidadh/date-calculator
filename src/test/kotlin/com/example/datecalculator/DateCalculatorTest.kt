package com.example.datecalculator

import com.example.datecalculator.exception.ParseException
import com.example.datecalculator.exception.ValidationException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

/**
 *
 * @author Arthur Kazemi<bidadh></bidadh>@gmail.com>
 */
internal class DateCalculatorTest {
  private val logger = Logger(System.out)
  private val dateValidator = DateValidator()
  private val dateParser = DateParser()

  @Test
  internal fun diff_whenPassInvalidString_throwsException() {
    val invalidDate = "Invalid Date"
    val throwable = ParseException("Invalid date")

    val mockParser = object : Parser {
      override fun parse(string: String): Date {
        throw throwable
      }
    }

    val dateCalculator = DateCalculator(mockParser, dateValidator, logger)

    val executable = Executable {
      dateCalculator.diff(invalidDate, invalidDate)
    }
    Assertions.assertThrows(ParseException::class.java, executable)
  }

  @Test
  internal fun diff_whenDatesAreInvalid_throwsException() {
    val invalidDate = "Invalid Date"
    val throwable = ValidationException("Invalid date")

    val mockValidator = object : Validator {
      override fun validate(date: Date) {
        throw throwable
      }
    }

    val mockParser = object : Parser {
      override fun parse(string: String): Date {
        return Date(1901, 1, 1)
      }

    }
    val dateCalculator = DateCalculator(mockParser, mockValidator, logger)

    val executable = Executable {
      dateCalculator.diff(invalidDate, invalidDate)
    }
    Assertions.assertThrows(ValidationException::class.java, executable)
  }

  @Test
  internal fun diff_whenValidParameters_returnsDiff() {
    val dateCalculator = DateCalculator(dateParser, dateValidator, logger)
    assertThat(dateCalculator.diff("1983-06-02", "1983-06-22")).isEqualTo(19)
    assertThat(dateCalculator.diff("1984-07-04", "1984-12-25")).isEqualTo(173)
    assertThat(dateCalculator.diff("1989-01-03", "1983-08-03")).isEqualTo(1979)
  }
}