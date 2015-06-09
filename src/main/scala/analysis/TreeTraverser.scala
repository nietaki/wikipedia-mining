package analysis
import scala.collection._
/**
 * reads the link dump from the file and performs DFS to get the statistics (ArticleProperties) for relevant nodes
 */
object TreeTraverser {

  /**
   * @return a graph mapping all entries to EntryProperties objects containing, amongst others, names of the entries
   *        that linked to this particular entry
   */
  def buildGraphRepresentation(entries: Iterator[Entry]): mutable.HashMap[String, EntryProperties] = {

    val graph = new mutable.HashMap[String, EntryProperties]()

    while(entries.hasNext) {
      val entry = entries.next()
      val entryProperties = graph.getOrElseUpdate(entry.firstLink, new EntryProperties())
      entryProperties.addParentEntry(entry.title)
    }
    graph
  }
}
