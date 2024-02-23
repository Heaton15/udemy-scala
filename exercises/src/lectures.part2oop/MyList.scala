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

  def map[B](t: MyTransformer[A, B]): MyList[B]
  def flatMap[B](t: MyTransformer[A, MyList[B]]): MyList[B]
  def filter(p: MyPredicate[A]): MyList[A]

  // concatenation
  def ++[B >: A](list: MyList[B]): MyList[B]
}

trait MyPredicate[-T] {
  def test(elem: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(ele: A): B
}

case object Empty extends MyList[Nothing] {

  def map[B](t: MyTransformer[Nothing, B]): MyList[B] = Empty
  def flatMap[B](t: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty
  def filter(p: MyPredicate[Nothing]): MyList[Nothing] = Empty

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

  def filter(predicate: MyPredicate[A]): MyList[A] = {
    if (predicate.test(h)) new Cons(h, t.filter(predicate))
    else t.filter(predicate)
  }

  def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
    new Cons(transformer.transform(h), t.map(transformer))
  }

  def ++[B >: A](list: MyList[B]): MyList[B] = {
    new Cons(h, t ++ list)
  }

  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = {
    transformer.transform(h) ++ t.flatMap(transformer)
  }
}

object InheritancePractice extends App {
  val list: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val cloneListOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
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

  println(
    list
      .map(new MyTransformer[Int, Int] {
        override def transform(elem: Int): Int = elem * 2
      })
      .toString
  )

  println(
    list
      .filter(new MyPredicate[Int] {
        override def test(elem: Int): Boolean = elem % 2 == 0
      })
      .toString
  )

  println((list ++ anotherListOfInts).toString)
  println(list.flatMap(new MyTransformer[Int, MyList[Int]] {
    override def transform(elem: Int): MyList[Int] = new Cons(elem, new Cons(elem+1, Empty))
  }))
}
