import java.io.{File, PrintWriter}

/**
 * Created by nietaki on 05.01.15.
 */
object Runner {
  def main(args: Array[String]): Unit = {
    println("starting")
    def articles ={
       WikipediaDumpParser.getNonRedirectTitlesAndContents(Config.wikipediaXmlLocation)
    }
    println("iterable retrieved")

    var i = 0;
    articles.foreach(t=> {
      //println("foo")
      //println(t._1)
      //println(t._2)
      if(i % 1000 == 0) {
        println(i)
      }
      i += 1;
    })

  }
}
