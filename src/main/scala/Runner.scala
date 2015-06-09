import ParsingStrategies._
import com.github.tototoshi.csv.CSVWriter
import utils.Config

/**
 * Created by nietaki on 05.01.15.
 */
object Runner {

  def getLinks(): Iterable[(String, Option[String])] = {
    println("starting")
    val articles = WikipediaDumpParser.getNonRedirectTitlesAndContents(Config.wikipediaXmlLocation)

    val strategies: List[ParsingStrategy] = List(removeBrackets, removeParentsContainingLinks, extractFirstLink, removeHashFragment)

    articles.map(pr => pr match {
      case (title, content) => (title, execute(strategies, content))
    })
  }
  def savePairsToCsv() = {
    val writer = CSVWriter.open(Config.pairsCsvLocation)
    def resultingDataset: Iterable[Seq[String]] = getLinks().filter(_._2.isDefined).map(pr => Seq(pr._1, pr._2.get))
    resultingDataset.foreach(writer.writeRow(_))
    writer.close();
  }

  def main(args: Array[String]): Unit = {
    //savePairsToCsv();
    //Persistence.printLineByLine(csvPath)
  }
}
