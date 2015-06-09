import java.io.FileInputStream

import org.xml.sax.InputSource
import scales.xml.ScalesXml._
import scales.xml._
/**
 * Created by nietaki on 23.05.15.
 */
object WikipediaDumpParser {
  val ns =  Namespace("http://www.mediawiki.org/xml/export-0.10/")

  def getPullXml(path: String): XmlPull = {
    // http://scala-scales.googlecode.com/svn/sites/scales/scales-xml_2.10/0.5.0/HowToUse.html
    // http://scala-scales.googlecode.com/svn/sites/scales/scales-xml_2.10/0.5.0/PullParsing.html
    // http://scala-scales.googlecode.com/svn/sites/scales/scales-xml_2.10/0.5.0-M1/doc/index.html#scales.xml.parser.pull.XmlPulls
    val inputStream = new FileInputStream(path)
    pullXml(new InputSource(inputStream))
  }

  def getPages(pull: XmlPull): Stream[XmlPath] = {
    val qnames = List(ns("mediawiki"), ns("page"))
    iterate(qnames, pull).toStream
  }

  def getTitleAndContent(element: XmlPath): (String, String) =  {
    val title = string(element.\*(ns("title")))
    val content = string(element.\*(ns("revision"))\*(ns("text")))
    (title, content)
  }

  def getTitlesAndContents(path: String): Stream[(String, String)] = getPages(getPullXml(path)).map(getTitleAndContent _)

  def getNonRedirectTitlesAndContents(path: String) = {
    getTitlesAndContents(path).filter(pr => ! pr._2.startsWith("#REDIRECT"))
  }
}
