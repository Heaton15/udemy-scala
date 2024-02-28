package lectures.part2oop

object Exceptions extends App {

  val x: String = null

  // println(x.length)
  // this ^^ will crash

  // Accessing method length throws the NullPointerException

  // We will learn how to "throw" and "catch" exceptions

  // 1. Throwing exceptions
  // val aWeirdValue: String = throw new NullPointerException

  // Classes must extend from the Throwable class
  // Exception and Error are the major Throwable classes
  // Exception: Denote something that went wrong with the program (NullPointerException)
  // Error: Denote something that went wrong with the system (StackOverflow)

  // 2. How to catch exceptions
  def getInt(withExceptions: Boolean): Int = {
    if (withExceptions) throw new RuntimeException("No int for you")
    else 42
  }

  // The try, catch, fail, tries to return AnyVal because unifying Int and Unit creates AnyVal
  val potentialFail =
    try {
      // code that might fail
      getInt(true)
    } catch {
      case e: RuntimeException => println("Caught a RuntimeException")

      // the finally block will always execute
    } finally {
      // finally blocks are optional
      // does not influence the return type of the expression
      // finally should be used for side effects, like logging to a file
      println("finally")
    }

  // 3. How to define your own exceptions
  class MyException extends Exception
  val exception = new MyException
  // throw exception

  /* 1. Crash your program with an OutOfMemoryError
   * 2. Crash with StackOverflowError
   * 3. PocketCalculator
   *  - add(x, y)
   *  - subtract(x, y)
   *  - multiply(x, y)
   *  - divide(x, y)
   *
   *  These methods should throw a custom exception if something wrong happens
   *  Throw
   *    - OverflowException if add(x,y) exceeds Int.MAX_VALUE
   *    - UnderflowException if subtract(x,y) exceeds Int.MIN_VALUE
   *    - MathCalculationException for division by 0
   *
   */

  // Task 1
  abstract class Infinite {
    def head: Int
    def add(x: Int): InfiniteList
  }

  object EmptyInfiniteList extends Infinite {
    def head: Int = throw new NoSuchElementException
    def add(x: Int): InfiniteList = InfiniteList(x, EmptyInfiniteList)
  }

  class InfiniteList(h: Int, t: Infinite) extends Infinite {
    def head: Int = h
    def add(x: Int): InfiniteList = InfiniteList(x, this)
  }

  // Task 1: OutOfMemoryError
  def forceOutOfMemory(x: Int, y: Seq[Int] = Seq(1)): Array[Byte] = {
    val arraySize = Integer.MAX_VALUE
    val arr = new Array[Byte](arraySize)
    arr
  }
  // forceOutOfMemory(1)

  // Task 2: StackOverFlow error
  def addForever(list: InfiniteList): Unit = {
    try {
      val l = list.add(list.head + 1)
      addForever(l)
    } catch {
      case e: StackOverflowError => println("OUT OF MEMORY WOO!")
    }
  }
  val myList = new InfiniteList(1, EmptyInfiniteList)
  // addForever(myList)

  // Task 3: PocketCalculator
  class PocketCalculator {
    def add(x: Int, y: Int) = {
      val result = x + y // Going over MaxValue will go negative
      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      else result
    }
    def subtract(x: Int, y: Int) = {
      val result = x - y
      if (x > 0 && y < 0 && result > 0) throw new OverflowException
      else if (x < 0 && y > 0 && result > 0) throw new UnderflowException
      else result
    }
    def multiply(x: Int, y: Int) = {
      val result = x * y
      if (x > 0 && y >0 && result < 0) throw new OverflowException
      else if (x < 0 && y < 0 && result < 0) throw new OverflowException
      else if (x > 0 && y < 0 && result > 0) throw new UnderflowException
      else if (x < 0 && y > 0 && result > 0) throw new UnderflowException
      else result
    }
    def divide(x: Int, y: Int) = {
      if (y == 0) throw new MathCalculationException
      else (x / y)
    }
  }

  class OverflowException extends Exception
  class UnderflowException extends Exception
  class MathCalculationException extends Exception

}
