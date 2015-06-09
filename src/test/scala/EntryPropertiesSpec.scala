import analysis.{DFSState, EntryProperties}
import org.specs2.mutable._

class EntryPropertiesSpec extends Specification {
  val sampleArticleName = "Science";
  def newEntryProperties() = new EntryProperties()

  "constructor" should {
    "set DFSState to NotVisited" in {
      newEntryProperties().state must be equalTo(DFSState.NotVisited)
    }

    "set weight to 1" in {
      newEntryProperties().weight must be equalTo(1)
    }

    "set no parents by default" in {
      newEntryProperties().parentEntryNames.isEmpty must beTrue
    }
  }

  "addParentEntry" should {
    "work on a default Entry" in {
      val entry = newEntryProperties()
      entry.addParentEntry("Foo")
      entry.parentEntryNames.length must be equalTo(1)
      entry.parentEntryNames(0) must be equalTo("Foo")
    }
  }
}