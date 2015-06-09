import ParsingStrategies._
import analysis.TreeTraverser
import com.github.tototoshi.csv.CSVWriter
import utils.{Persistence, Config}

/**
 * Created by nietaki on 05.01.15.
 */
object Runner {

  def getLinks(): Iterable[(String, Option[String])] = {
    println("starting")
    val articles = WikipediaDumpParser.getNonRedirectTitlesAndContents(Config.wikipediaXmlLocation)

    // TODO: fix for articles like http://en.wikipedia.org/w/index.php?title=Mercedes-Benz_W21&action=edit (fix extraction from 
    // TODO: remove all Wikipedia:Something articles
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
    Persistence.readEntriesFromCsv(Config.pairsCsvLocation).filter(_.firstLink.length < 2).foreach(println(_))

    return

    var graph = TreeTraverser.buildGraphRepresentation(Persistence.readEntriesFromCsv(Config.pairsCsvLocation))
    println(graph.size)
    graph = graph.filter({case (k, properties) =>
        properties.parentEntryNames.length > 10
    })
    graph.foreach({case (k, v) =>
      val count = v.parentEntryNames.size
      println(s"$k, $count")})
  }
}
