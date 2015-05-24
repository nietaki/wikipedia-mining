/**
 * Created by nietaki on 24.05.15.
 */
object TextParsers {
  val singleLinkRegexString = "\\[\\[([^|\\[\\]]*)(\\|[^|\\[\\]]*)??\\]\\]" //the first group here is the important one
  val redirectRegex = (s"^\\#REDIRECT\\s*$singleLinkRegexString")r
}
