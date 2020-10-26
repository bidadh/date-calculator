package com.example.datecalculator.model

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 */
data class Date(
  val year: Int,
  val month: Int,
  val day: Int
): Comparable<Date> {
  fun fullDaysFromStartOfTheMonth(): Int {
    return day
  }

  fun isBefore(other: Date): Boolean {
    return when {
      this.year < other.year -> {
        true
      }
      (this.year == other.year) && (this.month < other.month) -> {
        true
      }
      (this.year == other.year) && (this.month == other.month) -> {
        this.day < other.day
      }
      else -> false
    }
  }

  override fun compareTo(other: Date): Int {
    if(this.isBefore(other)) {
      return -1
    }

    if(other.isBefore(this)) {
      return 1
    }

    return 0
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other !is Date) return false

    if (year != other.year) return false
    if (month != other.month) return false
    if (day != other.day) return false

    return true
  }

  override fun hashCode(): Int {
    var result = year
    result = 31 * result + month
    result = 31 * result + day
    return result
  }

  companion object {
    val DAYS_PER_MONTH = listOf(
      0, 31, 28, 31, 30, 29, 30, 31, 31, 30, 30, 30, 31
    )
  }
}