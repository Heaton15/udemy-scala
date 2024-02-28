package lectures.part1basics
import scala.annotation.tailrec

object Recursion extends App {
  def factorial(n: Int): Int = {
    if (n <= 1) { 1 }
    else {
      println(s"Computing factorial of $n, I first need factorial of ${n - 1}")
      val result = n * factorial(n - 1)
      println(s"Computed factorial of $n")
      result
    }
  }
  println(factorial(10))
  // Causes a stack over flow error because the recursive stack is too big
  // println(factorial(5000))

  // This way of writing a recursive function works with a big number because it
  // does not blow up the stack
  def anotherFactorial(n: Int): BigInt = {
    // The @tailrec annotation makes the compiler check that this is in fact
    // tail recursive in the call
    @tailrec
    def factHelper(x: BigInt, accumulator: BigInt): BigInt = {
      if (x <= 1) accumulator
      else factHelper(x - 1, x * accumulator) // Tail recursion
      // TAIL RECURSION = use recursive call as the LAST expression
    }
    factHelper(n, 1)
    // factHelper(10, 1) = factHelper(10, 1)
    // = factHelper(9, 1*10)
    // = factHelper(8, 1*10*9)
    // = ...
    // = factHelper(1, 2 * 3 * 4 .. * 10 * 1)
  }
  // println(anotherFactorial(5000))

  // When you need loops, use TAIL RECURSION

  // factHelper is the last expression in the code path which allows scala to
  // preserve the stack frame and not use new stack frames for each recursive
  // call. This is a by product of tail recursion

  // The accumulator needs to store the intermediate. Sometimes you need more
  // than 1
  /* 1. Concatenate a string n times
   * 2. IsPrime function tail recursive
   * 3. Fibonacci function that is tail recursive
   */

  def catString(myStr: String, n: Int): String = {
    @tailrec
    def makeString(x: String, acc: Int): String = {
      if (acc <= 1) x
      else makeString(x + myStr, acc - 1)
    }
    makeString(myStr, n)
  }
  println(catString("Name", 5))

  // Prime if:
  // 1. Not divisible by 2
  // 2. Not divisible by all numbers from 1 to itself
  def isPrime(num: Int): Boolean = {
    @tailrec
    def isPrimeUntil(x: Int): Boolean = {
      if (x <= 1) true
      else if (num % x == 0) false
      else isPrimeUntil(x - 1)
    }
    isPrimeUntil(num / 2)
  }
  println(isPrime(2003))

  // f(5) = f(4) - f(3)
  // f(5) = (f(3) - f(2)) - f(3)
  // This might have not seemed that obvious, but it is a good example of
  // more complicated tail recursion in solving a an algorithm
  def fibonacciFunction(n: Int): Int = {
    @tailrec
    def fibCalc(i: Int, last: Int, nextToLast: Int): Int = {
      if ( i >= n) last
      else fibCalc(i + 1, last + nextToLast, last)
    }
    if (n <= 2) {1}
    else fibCalc(3, 1, 1)
  }
  println(fibonacciFunction(5))
}

