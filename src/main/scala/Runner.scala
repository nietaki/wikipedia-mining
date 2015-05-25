import java.io.{File, PrintWriter}
import ParsingStrategies._
import com.github.tototoshi.csv.CSVWriter

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

  def main(args: Array[String]): Unit = {
    val writer = CSVWriter.open(s"data/pairs.csv")
    def resultingDataset: Iterable[Seq[String]] = getLinks().filter(_._2.isDefined).map(pr => Seq(pr._1, pr._2.get))
    resultingDataset.foreach(writer.writeRow(_))
    writer.close();
  }
}
