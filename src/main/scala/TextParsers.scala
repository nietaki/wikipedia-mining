/**
 * Created by nietaki on 24.05.15.
 */
object TextParsers {
  val singleLinkRegexString = "\\[\\[([^|\\[\\]]*)(\\|[^|\\[\\]]*)??\\]\\]" //the first group here is the important one
  val redirectRegex = (s"^\\#REDIRECT\\s*$singleLinkRegexString")r
  val singleLinkRegex = (singleLinkRegexString)r

  val hashToTheEndOfTheLineRegex = "#.*$"r

  val nonParentChar = "[^)(]"
  val nonSquareBracketChar = "[^\\[\\]]"
  val parenthesesContainingLinks = (s"\\($nonParentChar*\\[\\[$nonSquareBracketChar*\\]\\]$nonParentChar*\\)")r

  def stringRemovalAccumulationStep(left: Char, right: Char, depth: Int, currentChar: Char): (Int, Option[Char]) = {
    if(currentChar == left) {
      (depth + 1, None)
    } else if (currentChar == right) {
      val newDepth = Math.max(depth - 1, 0)
      //we don't return the left and right characters anyways
      (newDepth, None)
    } else {
      if(depth > 0) {
        (depth, None)
      } else {
        (depth, Some(currentChar))
      }
    }
  }

  def removeTextBetweenCharacters(left: Char, right: Char, text: String): String = {
    val builder = new StringBuilder(text.length())
    def folder(depth: Int, currentChar: Char): Int = {
      val pair = stringRemovalAccumulationStep(left, right, depth, currentChar)
      pair match {
        case (newDepth, None) => {
          newDepth
        }
        case (newDepth, Some(c)) => {
          builder.append(c)
          newDepth
        }
      }
    }

    text.foldLeft(0)(folder _)

    builder.mkString
  }
}
