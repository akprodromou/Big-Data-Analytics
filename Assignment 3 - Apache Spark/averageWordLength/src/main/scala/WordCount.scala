import org.apache.spark.{SparkConf, SparkContext}

import org.apache.commons.io.FileUtils
import java.io.File

object WordCount {

  val inputFile = "/home/ozzy/Desktop/Spark_Word_Count/data/SherlockHolmes.txt"

  val outputFile = "/home/ozzy/Desktop/Spark_Word_Count/output/WordLengthAverage.txt"

  def main(args: Array[String]) {
    // create Spark context with Spark configuration
    val conf = new SparkConf()
      .setAppName("WordCountSpark")
      // run locally using all cores
      .setMaster("local[*]")

    val sc = new SparkContext(conf)

    // read in text file and split each document into words
    // sc returns an Resilient Distributed Dataset[String], where each element is one line of the file
    val path = if (args.nonEmpty) args(0) else inputFile
    val tokenized = sc.textFile(path)
      // take the lowerCase function and apply it independently to every element in the RDD
      .map(_.toLowerCase)
      // remove symbols
      .map(_.replaceAll("[^a-z0-9\\s]", " "))
      // in flatMap (flatten+map) the inner grouping of an item is removed and a sequence is generated
      .flatMap(_.split("\\s+"))
      // remove any empty strings
      .filter(_.nonEmpty)
      .filter(!_.charAt(0).isDigit)

    val byFirstLetter = tokenized
      // map the values with key being the first character, and value being the whole string
      .map(word => (word.charAt(0), (word.length, 1)))

    // each element of the RDD looks like this at this point:
    // (key, value) = (Char, (Int, Int))
    // The signature of reduceByKey is:
    // def reduceByKey(func: (V, V) => V): RDD[(K, V)]

    val sumsByKey = byFirstLetter
      .reduceByKey{
        case((len1, cnt1),(len2, cnt2)) =>
          (len1 + len2, cnt1 + cnt2)
      }

    val averageByKey = sumsByKey
      .mapValues {
        case(totalLen, totalCount) =>
          (totalLen.toDouble / totalCount).formatted("%.2f").toDouble
      }
      .sortBy(_._2, ascending = false)
    // delete the file in case it already exists (avoids an error)
    val dir = new File(outputFile)
    if (dir.exists()) FileUtils.deleteDirectory(dir)
    // save to file
    averageByKey.saveAsTextFile(outputFile)
    println("Program terminated")
    sc.stop()
  }
}
