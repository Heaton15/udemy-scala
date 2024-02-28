package lectures.part4pm

import scala.util.Random

object PatternMatching extends App {

  // switch on steroids

  val random = new Random
  val x = random.nextInt(10)

  val description = x match {
    case 1 => "the ONE"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "something else" // wildcard that can match anything
  }

  println(x)
  println(description)

  // 1. Decompose values with pattern matching like with case classes
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    // The if a < 21 is a guard like used in the past
    case Person(n, a) if a < 21 =>
      s"Hi, my name is $n and I can't drink in the US"
    case Person(n, a) => s"Hi, my name is $n and I am $a years old"

    // Compiler is telling us this needs to be null and not _
    case null => "I don't know who I am"
  }

  println(greeting)

  /* 1. cases are matched in order
   * 2. What if no cases match? You get a match error if you don't have
   *    wildcards
   * 3. What is the type of the pattern match expression? It is all types of the
   *    return type. If we return Ints and Strings, it will try to unify them.
   */

  // PM on sealed hierarchies
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal

  // NOTICE THIS: sealed classes help you cover your ass. You get compiler
  // warnings if you are not exhaustive in the search.
  //
  // If you chose to use regular classes, you won't have all of the sugar with
  // case classes and the matching issues there will be talked about in the
  // advanced course.

  val animal: Animal = Dog("Corgi")
  animal match {
    case Dog(someBreed) => println(s"Matched a dog of the $someBreed")
    case _              => println("Animal")
  }

  // match everything
  // This is super overkill. Return to your roots and use common sense
  val isEven = x match {
    case n if n % 2 == 0 => true
    case _               => false
  }

  // Don't do the dumb way with matching or conditionals. Come on, you already
  // get a Boolean from conditional solving
  val isEvenCond = if (x % 2 == 0) true else false
  val isEvenNormal = x % 2 == 0

  /* Exercises
   * 1. simple function uses PM
   *  takes Expr as parameter and returns human readable format
   *  Sum(Number(2), Number(3)) => 2 + 3 should be returned
   *  Sum(Number(2), Number(3), Number(4)) => 2 + 3 + 4
   *  Prod(Sum(Number(2), Number(1)), Number(3)) = (2 + 1) * 3
   *  Sum(Prod(Number(2), Number(1)), Number(3)) = 2 * 1 + 3
   */
  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  // Your solution was with Int but he wanted String. 
  def solveExpr(e: Expr): Int = {
    e match {
      case Sum(a, b) => solveExpr(a) + solveExpr(b)
      case Prod(a, b) => {
        def maybeShowParantheses(exp: Expr) = exp match {
          case Prod(_, _) => solveExpr(exp)
          case Number(_)  => solveExpr(exp)
          case _          => "(" + solveExpr(exp) + ")"
        }
        // maybeShowParantheses(e1) + " * " + maybeShowParantheses(e2)
        solveExpr(a) * solveExpr(b)
      }
      case Number(n) => n
    }
  }
  // val a = Prod(Sum(Number(2), Number(1)), Number(3))
  val a = Sum(Prod(Number(2), Number(1)), Number(3))
  val b = solveExpr(a)
  println(b)

}
