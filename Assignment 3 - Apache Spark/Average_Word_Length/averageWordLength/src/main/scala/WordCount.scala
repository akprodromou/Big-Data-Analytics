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
    // όρισε τα clusters (εδώ τον έναν υπολογιστή)
    val sc = new SparkContext(conf)

    // read in text file and split each document into words. If not specified in the CL, use the default inputFile
    val path = if (args.nonEmpty) args(0) else inputFile
    // sc returns an Resilient Distributed Dataset[String], where each element is one line of the file
    val tokenized = sc.textFile(path)
      // take the lowerCase function and apply it independently to every element in the RDD
      .map(_.toLowerCase)
      // remove symbols
      .map(_.replaceAll("[^a-z0-9\\s]", " "))
      // in flatMap (flatten+map) the inner grouping of an item is removed and a sequence is generated
      .flatMap(_.split("\\s+"))
      // filter selects all elements nonEmpty elements, i.e. removes any empty strings
      .filter(_.nonEmpty)
      .filter(!_.charAt(0).isDigit)

    // RDD elements at this point: RDD[string]

    val byFirstLetter = tokenized
      // map the values with key being the first character, and value being the whole string
      .map(word => (word.charAt(0), (word.length, 1)))

    // each element of the RDD looks like this at this point:
    // (key, value) = (Char, (Int, Int))

    // The signature of reduceByKey is: def reduceByKey(func: (V, V) => V): RDD[(K, V)]
    val sumsByKey = byFirstLetter
      .reduceByKey((a, b) => (a._1 + b._1, a._2 + b._2))

    // RDD elements at this point: RDD[(K, V)] = RDD[(K, (Total Length of individual Word, Total Occurence Count of Word))]

    val averageByKey = sumsByKey
      .mapValues {
        case(totalLen, totalCount) =>
          (totalLen.toDouble / totalCount).formatted("%.2f").toDouble
      }
      // Εφαρμόζουμε και την συνάρτηση sortBy(_._2) η οποία ταξινομεί τα αποτελέσματα
      // σε αύξουσα σειρά με βάση το value τους
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
