package lectures.part3fp

import scala.util.Random

object Sequences extends App {

  // Seq are a very general interface for something with:
  //  1. A well-defined order
  //  2. Indexable

  val aSequence = Seq(1, 3, 2, 4)
  println(aSequence)
  println(aSequence.reverse)
  println(aSequence(2))
  println(aSequence ++ Seq(7, 6, 5))
  println(aSequence.sorted)

  // Ranges, explored in advanced scala course
  val aRange: Seq[Int] = 1 until 10
  println(aRange)

  // List
  // lists are immutable and based on linear sequence
  // head, tail are in constant time. Getting elemnets are linear time.
  // Nil or :: are the subtypes

  // appending
  val aList = List(1, 2, 3)
  val prepended = 42 :: aList
  println(prepended)

  // fill
  val apples5 = List.fill(5)("apple")
  println(apples5)

  // mkString
  println(apples5.mkString("-|-"))

  // arrays
  val numbers = Array(1, 2, 3, 4)
  val threeElements = Array.ofDim[Int](3)
  println(threeElements) // obscure printing as a result
  threeElements.foreach(
    println
  ) // Default should be zero. null for reference types.

  // mutation
  numbers(2) =
    0 // numbers.update(2, 0) <-- mutable which we obviously don't use often
  println(numbers.mkString(" "))

  // arrays and seq
  // Becomes an ArraySeq, explained in advanced course
  val numbersSeq: Seq[Int] = numbers
  println(numbersSeq)

  // vectors
  // Default implementation for immutable sequences
  // fast element addition
  // good performance for very large sizes

  val vector: Vector[Int] = Vector(1, 2, 3)
  println(vector)

  // vectors vs lists
  val maxRuns = 1000
  val maxCap = 100000
  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new Random
    val times = for {
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCap), r.nextInt)
      System.nanoTime - currentTime
    }
    times.sum * 1.0 / maxRuns
  }

  val numbersList = (1 to maxCap).toList
  val numbersVector = (1 to maxCap).toVector

  // keeps references to tails
  // Updating elements in the middle takes a long time
  println(getWriteTime(numbersList))

  // Depth of the tree is small, so fast to lookup
  // Need to replace an entire 32 element chunk (no always a disadvantage)
  println(getWriteTime(numbersVector))

}
