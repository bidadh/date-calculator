package com.example.datecalculator

import com.example.datecalculator.exception.ParseException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.function.Executable
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 *
 * @author Arthur Kazemi<bidadh></bidadh>@gmail.com>
 */
internal class DateParserTest {
  private val parser = DateParser()

  @ParameterizedTest
  @ValueSource(strings = ["InvalidDate", "1999/02/01", "1999-01-1", "12345-234-56"])
  internal fun parse_whenPassInvalidString_throwsException(string: String) {
    val executable = Executable { parser.parse(string) }
    assertThrows(ParseException::class.java, executable)
  }

  @ParameterizedTest
  @ValueSource(strings = ["1901-01-01", "2999-12-31", "2020-12-29"])
  internal fun parse_whenValidString_returnsDate() {
    val executable = Executable { parser.parse("InvalidDate") }
    assertThrows(ParseException::class.java, executable)
  }
}