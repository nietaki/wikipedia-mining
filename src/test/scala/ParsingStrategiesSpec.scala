import org.specs2.mutable._

class ParsingStrategiesSpec extends Specification {
 
  "removeHashFragment" should {
    "not break stuff" in {
      ParsingStrategies.removeHashFragment("foo, bar baz").get must beEqualTo("foo, bar baz")
    }

    "get rid of anything from the hash to the end of the line" in {
      ParsingStrategies.removeHashFragment("foo#dfkfa, bar #baz").get must beEqualTo("foo")
    }
  }
}