import org.specs2.mutable._

class TextParsersSpec extends Specification {
 
  "redirectRegex" should {
    "recognize a simple redirect" in {
      val res = TextParsers.redirectRegex.findFirstMatchIn("#REDIRECT [[Transport in Afghanistan]] {{R from CamelCase}} {{R unprintworthy}}")
      val m = res.get
      m.group(1) mustEqual("Transport in Afghanistan")
    }

    "recognize a redirect without a space after #REDIRECT" in {
      val res = TextParsers.redirectRegex.findFirstMatchIn("#REDIRECT[[Transport in Afghanistan]] {{R from CamelCase}} {{R unprintworthy}}")
      val m = res.get
      m.group(1) mustEqual("Transport in Afghanistan")
    }

    "recognize a redirect to a named link" in {
      val res = TextParsers.redirectRegex.findFirstMatchIn("#REDIRECT[[Transport in Afghanistan|Afghani Transport]] {{R from CamelCase}} {{R unprintworthy}}")
      val m = res.get
      println(m.subgroups)
      m.group(1) mustEqual("Transport in Afghanistan")
    }
  }
}