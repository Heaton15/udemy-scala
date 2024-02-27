package lectures.part3fp

object MapFlatmapFilterFor extends App {

  // map, flatmap, filter are used extremely frequenly with collections

  val list = List(1, 2, 3)
  println(list.head)
  println(list.tail)

  // map
  println(list.map(_ + 1))

  // filter
  println(list.filter(_ % 2 == 0))

  // flatMap
  val toPair = (x: Int) => List(x, x + 1)
  println(list.flatMap(toPair))

  // print all combinations between two lists
  val numbers = List(1, 2, 3, 4)
  val chars = List('a', 'b', 'c', 'd')
  val colors = List("black", "white")

  // flatMap and map are used to iterate like for loops in other languages
  // "iterations"
  val combinations = chars.flatMap(i =>
    numbers.flatMap(x => colors.map(color => "" + x + i + color))
  )
  println(combinations)

  // foreach except it does not return anything like Map
  list.foreach(println)

  // for-comprehensions
  // Short hands to make the chaining more readable

  // This for comprehension is a flatMap / map chain to make this much more
  // readable, nice!
  val forCombinations = for {
    n <- numbers
    if n % 2 == 0 // <-- if conditional is a guard, translates to filter call like numbers.filter
    c <- chars
    color <- colors
  } yield ("" + c + n + "-" + color)

  for {
    n <- numbers
  } println(n)

  // syntax overload. This is insanely common.
  list.map { x => x * 2 }

  /* 1. See if MyList supports for comprehensions?
   *  map(f: A => B) => MyList[B]
   *  filter(p: A => Boolean) => MyList[A]
   *  flatMap(f: A => MyList[B]) => MyList[B]
   * 2. Implement a small collection of at most ONE element - Maybe[+T]
   *    - sub-types can be Empty OR single value element
   *    Implement map, flatMap, and filter for them
   *
   *
   *    The implementation for this will go in its own file
   */

}
