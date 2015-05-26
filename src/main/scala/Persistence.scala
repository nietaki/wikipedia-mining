import com.github.tototoshi.csv.CSVReader

/**
 * Created by nietaki on 26.05.15.
 */
object Persistence {
  def readFromCsv(path: String): Iterator[(String, String)] = {
    val reader = CSVReader.open(path)
    reader.iterator.flatMap(s => {
      s match {
        case h1 :: h2 :: _ => {
          List((h1, h2))
        }
        case _ => List()
      }
    })
  }

  def printLineByLine(pathToCsv: String) = {
    readFromCsv(pathToCsv).foreach(pair => {
      println(pair._1)
      println(pair._2)
    })
  }
}
