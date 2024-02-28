package lectures.part4pm

object PatternsEverywhere extends App {

  // big idea #1
  // Does it seem weird to you that we have all these cases inside of a try
  // catch?
  try {} catch {
    case e: RuntimeException       => "runtime"
    case npe: NullPointerException => "npe"
    case _                         => "something else"
  }

  // catches are actually MATCHES
  // This is what it looks like
  // try {
  //  // code
  // } catch
  //  (e) {
  //    e match {
  //      case e: RuntimeException       => "runtime"
  //      case npe: NullPointerException => "npe"
  //      case _                         => "something else"
  //    }
  //  }

  // big idea #2
  // generators are also based on PATTERN MATCHING!
  val list: List[Int] = List(1, 2, 3, 4)
  val evenOnes = for {
    x <- list if x % 2 == 0
  } yield (10 * x)

  val tuples = List((1, 2), (3, 4))
  val filterTuples = for {
    (first, second) <- tuples
  } yield first * second
  // case classes, :: operators, ...

  // big idea #3
  val tuple = (1, 2, 3)
  val (a, b, c) =
    tuple // similar to unpacking in other languages but the pattern matching does this
  println(b)
  // multiple value definitions based on PATTERN MATCHING
  // ALL THE POWER

  // In Scala3.2 this throws a warning now because there can be runtime errors
  // and now compile time can find it. In short, if the list is empty this will
  // fail so the assumption that it is not empty is important. @unchecked will
  // get rid of the warning.
  val newList = List.empty
  val head :: tail = list: @unchecked
  println(head)
  println(tail)

  // big idea #4 - NEW
  // partial functions <- based on PATTERN MATCHING
  val mappedList = list.map {
    case v if v % 2 == 0 => s"$v is even"
    case 1               => "the one"
    case _               => "something else"
  } // partial function literal (don't need the x => x match {})

  val mappedList2 = list.map { x =>
    x match {
      case v if v % 2 == 0 => s"$v is even"
      case 1               => "the one"
      case _               => "something else"
    }
  }
  println(mappedList)

}
