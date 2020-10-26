package com.example.datecalculator

import com.example.datecalculator.exception.ValidationException
import com.example.datecalculator.model.Date
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

/**
 *
 * @author Arthur Kazemi<bidadh></bidadh>@gmail.com>
 */
internal class DateValidatorTest {
  private val validator = DateValidator()

  @Test
  fun validate_whenYearIsBefore1901_throwsException() {
    val executable = Executable { validator.validate(Date(1900, 1, 1)) }
    Assertions.assertThrows(ValidationException::class.java, executable)
  }

  @Test
  fun validate_whenYearIsAfter2999_throwsException() {
    val executable = Executable { validator.validate(Date(3000, 1, 1)) }
    Assertions.assertThrows(ValidationException::class.java, executable)
  }

  @Test
  fun validate_whenValidDate_passes() {
    validator.validate(Date(2999, 1, 1))
  }
}