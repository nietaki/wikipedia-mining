package utils

import com.github.tototoshi.csv.CSVWriter

/**
 * Created by nietaki on 10.01.15.
 */
object Utils {
  def myParseInt(s: String): Int= {
    try {
      s.toInt
    } catch {
      case _: Exception => -1
    }
  }

  /**
   * @param data sequence of rows containing data
   * @param filename filename to be saved to
   */
  def saveToCsv(data: Seq[Seq[Any]], filename: String): Unit = {
    val writer = CSVWriter.open(s"data/$filename.csv")
    writer.writeAll(data)
  }
}
