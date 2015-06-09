/**
 * Created by nietaki on 25.05.15.
 */
object ParsingStrategies {
  type ParsingStrategy = String => Option[String]
  def removeBrackets(input: String): Option[String] = {
    Some(TextParsers.removeTextBetweenCharacters('{', '}', input))
  }

  def removeParentsContainingLinks(input: String): Option[String] = {
    Some(TextParsers.parenthesesContainingLinks.replaceAllIn(input, ""))
  }

  def extractFirstLink(input: String): Option[String] = {
    val res = TextParsers.singleLinkRegex.findFirstMatchIn(input)
    res.map(_.group(1))
  }

  def removeHashFragment(input: String): Option[String] = {
    Some(TextParsers.hashToTheEndOfTheLineRegex.replaceFirstIn(input, ""))
  }

  def execute(strategies: Iterable[ParsingStrategy], input: String): Option[String] = {
    val inputOption: Option[String] = Some(input)
    strategies.foldLeft(inputOption)((io, strat) => io.flatMap(strat))
  }
}
