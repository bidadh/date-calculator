import com.example.datecalculator.DateCalculator
import com.example.datecalculator.Logger

fun main(args: Array<String>) {
  val logger = Logger(System.out)
  if(args.size != 2) {
    logger.error("Invalid number of parameters!")
    logger.info("Please enter 'start' and 'end' date in the format of 'YYYY-mm-dd'")

    return
  }
  val application = DateCalculator.instance
  try {
    val start = args[0]
    val end = args[1]
    val diff: Int = application.diff(start, end)
    logger.answer("Number of full days from '$start' to '$end' is: ", "$diff")
  } catch (ex: Exception) {
    logger.error(ex.localizedMessage)
  }
}

