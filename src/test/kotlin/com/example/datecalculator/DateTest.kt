package com.example.datecalculator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

/**
 *
 * @author Arthur Kazemi<bidadh></bidadh>@gmail.com>
 */
internal class DateTest {
  @Test
  internal fun isBefore_whenDateIsBefore_returnsTrue() {
    val date = Date(2020, 10, 23)
    assertThat(date.isBefore(Date(2020, 10, 24))).isTrue
    assertThat(date.isBefore(Date(2020, 11, 24))).isTrue
    assertThat(date.isBefore(Date(2021, 1, 1))).isTrue

    assertThat(date.isBefore(Date(2020, 10, 23))).isFalse
    assertThat(date.isBefore(Date(2020, 9, 24))).isFalse
    assertThat(date.isBefore(Date(2019, 1, 1))).isFalse
  }

  @Test
  internal fun compareTo_whenDateIsBeforeOther_returnsNegative() {
    val date = Date(2020, 10, 23)
    val other = Date(2020, 10, 24)
    assertThat(date.compareTo(other)).isEqualTo(-1)
  }

  @Test
  internal fun compareTo_whenDateIsAfterOther_returnsPositive() {
    val date = Date(2020, 10, 23)
    val other = Date(2020, 10, 22)
    assertThat(date.compareTo(other)).isEqualTo(1)
  }

  @Test
  internal fun compareTo_whenDatesAreEqual_returnsZero() {
    val date1 = Date(2020, 10, 23)
    val date2 = Date(2020, 10, 23)
    assertThat(date1.compareTo(date2)).isEqualTo(0)
  }
}