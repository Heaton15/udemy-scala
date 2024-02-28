package lectures.part4pm

import lectures.part2oop.MyList
import lectures.part2oop.Empty
import lectures.part2oop.Cons

object AllThePatterns extends App {

  // 1 - constants
  val x: Any = "Scala"

  val contants = x match {
    case 1              => "a number"
    case "Scala"        => "THE Scala"
    case true           => "The Truth"
    case AllThePatterns => "A singleton object"
  }

  // 2 - match anything
  // 2.1 wildcard

  val matchAnything = x match {
    case _ =>
  }

  // 2.2 variable
  val matchAVariable = x match {
    case something => s"I've found $something"
  }

  // 3 - tuples
  val aTuple = (1, 2)
  val matchATuple = aTuple match {
    case (1, 1)         =>
    case (something, 2) => s"I've found $something"
  }

  val nestedTuple = (1, (2, 3))
  val matchANestedTuple = nestedTuple match {
    case (_, (2, v)) =>
  }
  // PMs can be NESTED! nice

  // 4 - case classes - constructor pattern
  // PMs can be nested with CCs as well

  val aList: MyList[Int] = Cons(1, Cons(2, Empty))
  val matchAList = aList match {
    case Empty                           =>
    case Cons(h, Cons(subhead, subtail)) =>
  }

  // 5 - list patterns
  val aStandardList = List(1, 2, 3, 42)
  val standardListMatching = aStandardList match {
    case 1 :: List(_)        => // infix pattern with lots of magic
    case List(1, _, _, _)    => // extractor for list - advanced
    case List(1, _*)         => // List of arbitrary length - advanced
    case List(1, 2, 3) :+ 42 => // infix pattern
    case _                   =>
  }

  // 6 - type specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    // Commenting out to get rid of the warning for now
    // case list: List[Int] => // explicit type specifier
    case _ =>
  }

  // 7 - name binding
  val nameBindingMatch = aList match {
    case Cons(1, rest @ Cons(2, _)) => // name binding inside nested patterns
    case nonEmptyList @ Cons(
          _,
          _
        ) => // name binding that names the pattern => use the name later(here)
  }

  // 8 - multi-patterns
  val multipatterns = aList match {
    case Empty | Cons(
          0,
          _
        ) => // compound pattern (multi-pattern). Multiple patterns can trigger
  }

  // 9 - if guards
  val secondElementSpecial = aList match {
    case Cons(_, Cons(specialElement, _))
        if specialElement % 2 == 0 => // the if guard will filter out the pattern
  }

  // ALL.

  /* Question.
   */

  val numbers = List(1, 2, 3)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "a list of strings"
    case listOfNumbers: List[Int]    => "a list of numbers"
    case _                           => ""
  }

  // You would think this would return a list of numbers but it returns a list
  // of strings.
  // This is a JVM trick question
  // Long story short, JVM was designed for Java language with backwards
  // compatibility in mind.
  // Java1 was oblivious to generic types, so type erasure obliterated it since
  // future Java versions had to be backwards compatible.
  println(numbersMatch)

}
