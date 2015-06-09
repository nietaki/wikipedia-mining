import analysis.{DFSState, EntryProperties}
import org.specs2.mutable._

class EntryPropertiesSpec extends Specification {
  val sampleArticleName = "Science";

  "constructor" should {
    "set DFSState to NotVisited" in {
      EntryProperties().state must be equalTo(DFSState.NotVisited)
    }

    "set weight to 1" in {
      EntryProperties().weight must be equalTo(1)
    }

    "set no parents by default" in {
      EntryProperties().parentEntryNames.isEmpty must beTrue
    }
  }

  "addParentEntry" should {
    "work on a default Entry" in {
      val entry = EntryProperties()
      entry.addParentEntry("Foo")
      entry.parentEntryNames.length must be equalTo(1)
      entry.parentEntryNames(0) must be equalTo("Foo")
    }
  }

  "equality operator" should {
    "be reference based" in {
      (EntryProperties() == EntryProperties()) must beFalse
    }
  }
}