package lectures.part2oop
/*
 * This MyList file was a learning exercise that continued to expand over the
 * course of the Rock the JVM course. The big takeaways are the follwing:
 *
 * 1. Variance with +T, T, -T and its implications. This is covered more in the
 *    advanced course. Might want to start here to understand "position" better
 *    in classes, methods, return types and such.
 *
 * 2. Recursion to implement functions such as map, filter, flatMap
 * 3. Inheritance from an abstract class and implementing traits (Predicate /
 *    Transformer) with anonymous classes.
 *    - Note that the Predicate and Transformer anonymous classes can also
 *      be defined separately and passed as a function parameter. There is
 *      undoubtedly a better way to transform Int to String, Int to Double, etc
 *      etc.
 */

abstract class MyList[+A] {
  /* head = first element of the list
   * tail = remainder of the list
   * isEmpty = is this list empty
   * add(int) => new list with this element added
   * toString => a string representation of the list
   */

  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  def printElements: String

  // toString is already declared so we have to override it
  override def toString: String = "[" + printElements + "]"
  /* 1. Generic trait MyPredicate[-A]
   *  - test[A]: Boolean
   * 2. Generic trait MyTransformer[-A, B] , has small method to convert type A to type B
   *  -
   * 3. MyList:
   *  - map(transformer) => MyList
   *  - filter(predicate) => MyList
   *  - flatMap(transformer from A to MyList[B]) => MyList[B]
   *
   * class EvenPredicate extends MyPredicate[Int]
   *  - test[A] will take Int and see if it is even or not
   *
   * class StringToIntTransformer extends MyTransformer[String, Int]
   *  - transform(A) => B
   *
   * [1, 2, 3].map(n*2) = (2, 4, 6)
   * [1,2,3,4].filter(n % 2) = (2, 4)
   * [1,2,3].flatMap(n => [n, n+1]) => [1,2,2,3,3,4]
   *
   *
   */

  /* Now that we are in the functional programming section, the first task was
   * to remove the MyPredicate and MyTransformer traits since they resemble too
   * much of OOP. Because we have Function1, Function2, etc, we can implement
   * these in a much more function way. As a result, our types will be function
   * types instead of objects, and the implementations will come from the
   * scala Functions.
   *
   * note: MyTransformer was taking A and returning B which is A => B
   * note: MyPredicate was taking T and returning Boolean which is T => Boolean
   */

//trait MyPredicate[-T] {
//  def apply(elem: T): Boolean
//  //def test(elem: T): Boolean
//}
//
//trait MyTransformer[-A, B] {
//  def apply(ele: A): B
//  //def transform(ele: A): B
//}

  def map[B](t: A => B): MyList[B]
  def flatMap[B](t: A => MyList[B]): MyList[B]
  def filter(p: A => Boolean): MyList[A]

  // concatenation
  def ++[B >: A](list: MyList[B]): MyList[B]
}

case object Empty extends MyList[Nothing] {

  def map[B](t: Nothing => B): MyList[B] = Empty
  def flatMap[B](t: Nothing => MyList[B]): MyList[B] = Empty
  def filter(p: Nothing => Boolean): MyList[Nothing] = Empty

  def head: Nothing = throw new NoSuchElementException // throws Nothing type
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  // The covariance of MyList translates into B can be any supertype of Nothing (which is everything).
  // Without the covariance in the MyList definition, this add function would
  // break. We have to make it so that Box[Nothing] and all boxes of supertypes
  // of Nothing are compatible.
  def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)
  def printElements: String = ""
  def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
}

// Adding case implements equals and hashCode and it is not serializable
case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  def head: A = h
  def tail: MyList[A] = t
  def isEmpty: Boolean = false
  def add[B >: A](element: B): MyList[B] = new Cons(element, this)
  def printElements: String = {
    if (t.isEmpty) "" + h
    else s"$h ${t.printElements}"
  }

  def filter(predicate: A => Boolean): MyList[A] = {
    if (predicate(h)) new Cons(h, t.filter(predicate))
    else t.filter(predicate)
  }

  def map[B](transformer: A => B): MyList[B] = {
    new Cons(transformer(h), t.map(transformer))
  }

  def ++[B >: A](list: MyList[B]): MyList[B] = {
    new Cons(h, t ++ list)
  }

  def flatMap[B](transformer: A => MyList[B]): MyList[B] = {
    transformer(h) ++ t.flatMap(transformer)
  }
}

object InheritancePractice extends App {
  val list: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val cloneListOfIntegers: MyList[Int] =
    new Cons(1, new Cons(2, new Cons(3, Empty)))
  val anotherListOfInts: MyList[Int] = new Cons(4, new Cons(5, Empty))
  val listStrings: MyList[String] =
    new Cons("hello", new Cons("Scala", new Cons("YAY", Empty)))
  println(list.toString)
  println(listStrings.toString)
  println(cloneListOfIntegers == list)

  // Note that anonymous functions are being used to implement the Predicate and
  // Transform functions.
  // The confusing part originally was how we implemented them in the traits,
  // but clearly the intention was to implement them after the fact

  /* Now that we are in the functional programming section, we don't need the
   * MyTransformer / MyPredicate calls anymore. Instead, we can define the
   * Function types here in the anonymous implementations.
   *
   * INSTEAD of passing in anonymous functions here, we can now just pass a
   * function as a the input to list.map(...) instead of expecting the trait. We
   * can now provide different implementations which just take a Int and return
   * an Int. 
   *
   * higher order functions take functions as inputs or return functions
   */

  println(
    list
      .map(new Function1[Int, Int] {
        override def apply(elem: Int): Int = elem * 2
      })
      .toString
  )

  println(
    list
      .filter(new Function1[Int, Boolean] {
        override def apply(elem: Int): Boolean = elem % 2 == 0
      })
      .toString
  )

  println((list ++ anotherListOfInts).toString)
  println(list.flatMap(new Function1[Int, MyList[Int]] {
    override def apply(elem: Int): MyList[Int] =
      new Cons(elem, new Cons(elem + 1, Empty))
  }))
}
