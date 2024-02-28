package lectures.part4pm

object BracelessSyntax extends App {

  // if - expressions

  val anIfExpression = if (2 > 3) "bigger" else "smaller"

  // java-style
  val anIfExpreesion_v2 =
    if (2 > 3) {
      "bigger"
    } else {
      "smaller"
    }

  // compact
  val anIfExpreesion_v3 =
    if (2 > 3) "bigger"
    else "smaller"

  // scala 3
  val anIfExpreesion_v4 =
    if 2 > 3 then "bigger" // must have higher indentation
    else "smaller"

  // This loose code is a new syntax for scala in general
  // If you are working with a Scala 2 codebase, you might not want to use these
  // new Scala 3 constructs
  val anIfExpreesion_v5 =
    if 2 > 3 then
      val result = "bigger"
      result
    else
      val result = "smaller"
      result

  val anIfExpreesion_v6 = if 2 > 3 then "bigger" else "smaller"

  // for comprehensions

  val aForComprehension = for {
    n <- List(1, 2, 3)
    s <- List("black", "white")
  } yield s"$n$s"

  // Scala3 style
  val aForComprehension_v2 =
    for
      n <- List(1, 2, 3)
      s <- List("black", "white")
    yield s"$n$s"

  // pattern matching
  val meaningOfLife = 42

  val aPatternMatch = meaningOfLife match {
    case 1 => "the one"
    case 2 => "double or nothing"
    case _ => "something else"
  }

  // Scala 3 version
  val aPatternMatch_v2 = meaningOfLife match
    case 1 => "the one"
    case 2 => "double or nothing"
    case _ => "something else"

  // methods without braces
  def computeMeaningOfLife(arg: Int): Int =
    val partialResult = 40
    partialResult + 2

  // Scala3 definitions
  class Animal:
    def eat(): Unit =
      println("I'm eating")
    def grow(): Unit =
      println("I am growing")

  // 3000 more lines of code
  end Animal // contextual keyword to help readabilitry as well as inform the compiler the class is done
  // for, if, match, for, methods, clases, traits, enums, objects can all have
  // the "end" keyword used.

  // anonymous classes
  val aSpecialAnimal = new Animal:
    override def eat(): Unit = println("I'm special")

  // indentation = strictly larger indentation than the previous line
  // 3 spaces + 2 tabs > 2 spaces + 2 tabs so that works
  // 3 spaces + 2 tabs > 3 spaces + 1 tab
  // 3 tabs + 2 spaces ??? 2 tabs + 3 spaces

}
