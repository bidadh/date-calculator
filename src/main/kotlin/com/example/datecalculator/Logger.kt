package com.example.datecalculator

import java.io.PrintStream
import java.util.stream.Stream

/**
 *
 * @author Arthur Kazemi<bidadh@gmail.com>
 */
class Logger(private val writer: PrintStream) {
  private fun write(vararg messages: String) {
    Stream.of(*messages).forEach { s: String? ->
      writer.print(
        s
      )
    }
  }

  private fun writeln(vararg messages: String) {
    Stream.of(*messages).forEach {
      write(it)
    }
    writer.println(clearFormats())
  }

  fun info(message: String) {
    writeln(infoMessage(message))
  }

  fun answer(message: String, answer: String) {
    writeln(infoMessage(message), successMessage(answer))
  }

  fun error(message: String) {
    writeln(errorMessage(message))
  }
  companion object {
    private const val CLEAR = "[0m"
    private const val RED = "[31m"
    private const val GREEN = "[32m"
    private const val CYAN = "[36m"
    private const val EMPTY = ""
    private const val ESCAPE_CODE = 27.toChar()
    fun clearFormats(): String {
      return formatMessage(CLEAR, EMPTY)
    }

    private fun formatMessage(code: String, message: String): String {
      return ESCAPE_CODE.toString() + code + message
    }

    fun infoMessage(message: String): String {
      return formatMessage(CYAN, message)
    }

    fun successMessage(answer: String): String {
      return formatMessage(GREEN, answer)
    }

    fun errorMessage(message: String): String {
      return formatMessage(RED, message)
    }
  }
}
