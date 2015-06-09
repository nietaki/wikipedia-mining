import analysis.{DFSState, EntryProperties, TreeTraverser, Entry}
import org.specs2.mutable._

class TreeTraverserSpec extends Specification {

  "buildGraphRepresentation()" should {
    "correctly construct an empty graph" in {
      val l = List[Entry]()
      val graph = TreeTraverser.buildGraphRepresentation(l.iterator)
      graph.size must be equalTo(0)
    }

    def mustContainSameValues(ep1: EntryProperties, ep2: EntryProperties): Boolean = {
      ep1.weight must be equalTo ep2.weight
      ep1.state must be equalTo ep2.state
      ep1.parentEntryNames must be equalTo ep2.parentEntryNames
    }

    "correctly construct a graph with one entry" in {
      val l = List[Entry](Entry("Article", "First Link"))
      val graph = TreeTraverser.buildGraphRepresentation(l.iterator)
      graph.size must be equalTo(1)
      graph.keys.head must beEqualTo("First Link") //yes, it's a map from the links to the entries that linked to them
      val entryProperties = graph("First Link")
      mustContainSameValues(entryProperties, EntryProperties(List("Article"), 1, DFSState.NotVisited))
    }

    "correctly construct a graph with two different entries" in {
      val l = List[Entry](Entry("Article", "First Link"), Entry("Foo", "Bar"))
      val graph = TreeTraverser.buildGraphRepresentation(l.iterator)
      graph.size must be equalTo(2)
      mustContainSameValues(graph("First Link"), EntryProperties(List("Article"), 1, DFSState.NotVisited))
      mustContainSameValues(graph("Bar"), EntryProperties(List("Foo"), 1, DFSState.NotVisited))
    }

    "correctly construct a graph with two entries leading to the same link" in {
      val l = List[Entry](Entry("Article", "First Link"), Entry("Foo", "First Link"))
      val graph = TreeTraverser.buildGraphRepresentation(l.iterator)
      graph.size must be equalTo(1)
      mustContainSameValues(graph("First Link"), EntryProperties(List("Foo", "Article"), 1, DFSState.NotVisited))
    }

  }
}