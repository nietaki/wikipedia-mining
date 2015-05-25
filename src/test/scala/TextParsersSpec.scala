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

  "parentsContainingStrings" should {
    "find nothing in a simple text" in {
      val res = TextParsers.parenthesesContainingLinks.findFirstMatchIn("fooo bar [[foobar]]")
      res.isEmpty must beTrue
    }

    "find something in a text containing a link inside a parent" in {
      val res = TextParsers.parenthesesContainingLinks.findFirstMatchIn("fooo (bar [[foobar]] bar) baz")
      res.isEmpty must beFalse
    }

    "find the correct parent, parent included" in {
      val res = TextParsers.parenthesesContainingLinks.findFirstMatchIn("fooo (bar [[foobar]] bar) baz")
      res.get.group(0) must beEqualTo("(bar [[foobar]] bar)")
    }

    "behave correctly with links with parentheses inside" in {
      val res = TextParsers.parenthesesContainingLinks.findFirstMatchIn("fooo (bar [[foo (bar)]] bar) baz")
      res.get.group(0) must beEqualTo("(bar [[foo (bar)]] bar)")
    }
  }
  "removeTextBetweenCharacters" should {
    "not modify an irrelevant sequence" in {
      val seq = "dlfkldkfadf3 df sfd"
      TextParsers.removeTextBetweenCharacters('{', '}', seq) must beEqualTo(seq)
    }
    "remove one character between brackets" in {
      val seq = "foo {a}bar"
      TextParsers.removeTextBetweenCharacters('{', '}', seq) must beEqualTo("foo bar")
    }

    "remove everything correctly in a complicated situation" in {
      val seq = "foo{a{b}      {{c}}} {{baz}}baz"
      TextParsers.removeTextBetweenCharacters('{', '}', seq) must beEqualTo("foo baz")
    }
  }

  "stringRemovalAccumulationStep" should {
    "return None if it is at positive depth" in {
      TextParsers.stringRemovalAccumulationStep('{', '}', 3, 'a') must beEqualTo((3, None))
    }

    "return same char if it is at zero depth" in {
      TextParsers.stringRemovalAccumulationStep('{', '}', 0, 'a') must beEqualTo((0, Some('a')))
    }

    "increase depth if it is at zero depth" in {
      TextParsers.stringRemovalAccumulationStep('{', '}', 0, '{') must beEqualTo((1, None))
    }

    "increase depth if it is at positive depth" in {
      TextParsers.stringRemovalAccumulationStep('{', '}', 3, '{') must beEqualTo((4, None))
    }

    "increase depth if it is at positive depth" in {
      TextParsers.stringRemovalAccumulationStep('{', '}', 3, '}') must beEqualTo((2, None))
    }
  }
}