package lectures.part3fp

object HOFsCurries extends App {

  // Nothing stops us from doing this, but how do you read this?
  // The final value looks like it will be an Int

  // Higher Order Functions (HOF)
  val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) =
    null

  // map, flatMap, filter in MyList
  // function that applies a function n times over a value x
  // nTimes(f, n, x), f is function, n is number of times, x is the input
  // nTimes(f,  3, x) = 3 f(f(f(x))) = nTimes(f, 2, f(x)) = nTimes(f, 1 f(f(x)))
  // nTimes(f, n, x) = f(f(...(x))) = nTimes(f, n-1, f(x))

  // Functional programming is basically abstract math, nice
  def nTimes(f: Int => Int, n: Int, x: Int): Int = {
    if (n <= 0) x
    else nTimes(f, n - 1, f(x))
  }

  val plusOne = (x: Int) => x + 1
  println(nTimes(plusOne, 10, 1))

  // Instead of making nTimes return our value, we could have it return a
  // function which we can now apply all over the place for what we need.

  // In this case, we don't return an Int, we return the function which gets us
  // our result
  // inc10 = ntb(plusOne, 10)
  // val y = increment10(1)

  // This might seem overkill for adding, but this kind of thinking becomes much
  // more intuitive later on.
  def nTimesBetter(f: Int => Int, n: Int): (Int => Int) = {
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTimesBetter(f, n - 1)(f(x))
  }
  val plus10 = nTimesBetter(plusOne, 10)
  println(plus10(1))

  // curried functions
  val superAdder = (x: Int) => (y: Int) => x + y
  val add3 = superAdder(3) // y => 3 + y
  println(add3(10))

  // functions with multiple parameter lists
  // These act like curried functions
  def curriedFormatter(c: String)(x: Double): String = c.format(x)

  // The types are required because it cannot figure out the type
  val standardFormat: (Double => String) = curriedFormatter("%4.2f")
  val preciseFormat: (Double => String) = curriedFormatter("%10.8f")

  println(standardFormat(Math.PI))
  println(preciseFormat(Math.PI))

  /* 1. Expand MyList
   *  - foreach method ( A => Unit ) applied to every element
   *      [1,2,3].foreach(x => println(x)), should print on every line
   *
   *  - sort function ((A, A) => Int) => MyList
   *      [1,2,3].sort((x,y) => y - x) , returns 3, 2, 1
   *
   *    - Receives a sorting function which compares 2 functions, check if one
   *    of the As is greater than the other As and vice versa.
   *
   *  - zipWith(list, (A, A) => B) => MyList[B]
   *    [1,2,3].zipWith([4,5,6], x * y) => [4, 10, 18]
   *
   *  - fold (should be curried method)
   *    fold(start)(function)
   *    [1,2,3].fold(0)(x + y) = 6
   *      - Add first element to start value and then the subsequent values
   *        added to the prior result
   *
   * 2. toCurry(f, (Int, Int) => Int) => (Int => Int => Int)
   *    fromCurry(f: (Int => Int => Int)) => (Int, Int) => Int
   *
   * 3. compose(f, g) => x => f(g(x))
   *    andThen(f, g) => x => g(f(x))
   */

  // Task 2
  // f is a function that takes 2 Ints and returns a function
  // Return type is a curried function
  // f: (Int, Int) => Int): Int => Int => Int = {
  def toCurry(f: (Int, Int) => Int): Int => Int => Int = {
    (x1: Int) => (x2: Int) => f(x1, x2)
  }

  // Input is a curried function
  // Convert the curried function to a non-curried version
  def fromCurry(f: Int => Int => Int): (Int, Int) => Int = {
    (x1: Int, x2: Int) => f(x1)(x2)
  }

  def compose[A,B,T](f: A => B, g: T => A): T => B = x => f(g(x))
  def andThen[A,B,C](f: A => B, g: B => C): A => C = x => g(f(x))

  def superAdder2: (Int => Int => Int) = toCurry(_ + _)
  def add4 = superAdder2(4)
  println(add4(17))

  val simpleAdder = fromCurry(superAdder2)
  println(simpleAdder(4, 17))

  val add2 = (x: Int) => x + 2
  val times3 = (x: Int) => x * 3
  val composed = compose(add2, times3)
  val ordered = andThen(add2, times3)

  println(composed(4))
  println(ordered(4))

}
