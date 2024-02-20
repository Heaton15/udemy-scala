package lectures.part1basics

import scala.annotation.tailrec

object Functions extends App {

  def aFunction(a: String, b: Int): String =
    a + " " + b

  println(aFunction("hello", 3))

  def aParameterlessFunction(): Int = 42
  println(aParameterlessFunction())
  // This will tell you that you need () since the function has a() in the
  // definition
  // println(aParameterlessFunction)

  // Need a return type since the compiler cannot resolve the return type on its
  // own. Always include return types for functions by default, anyways
  def aRepeatedFunction(aString: String, n: Int): String = {
    if (n == 1) aString
    else aString + aRepeatedFunction(aString, n - 1)
  }

  // Function knows the recursive property
  println(aRepeatedFunction("hello", 3))

  // When you need loops, use recursion. This is a fundamental idea of
  // functional programming. Don't use loops when this is available

  // Returns Unit and does nothing
  def aFunctionWithSideEffects(aString: String): Unit = println(aString)

  // Auxillary functions inside a function can exist
  def aBigFunction(n: Int): Int = {
    def aSmallerFunction(a: Int, b: Int): Int = a + b
    aSmallerFunction(n, n - 1)
  }

  /* 1. A greeting function (name, age) => "Hi, my name is $name and I am $age years old"
   * 2. Factorial function 1 * 2 * 3 * 4 .. * n (recursion)
   * 3. A Fibonacci function
   *  f(1) = 1
   *  f(2) = 1
   *  f(3) = 1 + 1 = 2
   *  f(4) = 2 + 1 = 3
   *  f(5) = 3 + 2 = 5
   *  f(n) = f(n-1) + f(n-2)
   * 4. Tests if a number is prime
   */

  // Task 1
  def greetingFunction(name: String, age: Int): Unit = {
    println(s"Hi, my name is $name and I am $age years old")
  }
  println(greetingFunction("david", 12))

  // Task 2
  def factorialFunction(n: Int): Int = {
    if (n <= 0) { 1 }
    else { n * factorialFunction(n - 1) }
  }
  println(factorialFunction(5))

  // Task 3
  def fibonacciFunction(n: Int): Int = {
    // Probably important to not that n <= 0 wasn't accounted for
    if (n == 1 || n == 2) { 1 }
    else { fibonacciFunction(n - 1) + fibonacciFunction(n - 2) }
  }
  println(fibonacciFunction(8))

  // Task 4
  def isPrime(n: Int): Boolean = {
    def isPrimeUntil(t: Int): Boolean = {
      // <= 1 is a prime number
      if (t <= 1) true
      // 
      else (n % t != 0 && isPrimeUntil(t-1))
    }
    isPrimeUntil(n / 2)
  }
  // This implementation checks it does not divide against 2 and that all values
  // up to n are not divisible into the number either.
  println(isPrime(5))
  // isPrime(5)
  // (5 % 2.5 != 0 && (5 % 4 != 0) && (5 % 3 != 0)
}
