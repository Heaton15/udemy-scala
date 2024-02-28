package lectures.part3fp

abstract class Maybe[+T] {
  def map[B](f: T => B): Maybe[B]
  def flatMap[B](f: T => Maybe[B]): Maybe[B]
  def filter(p: T => Boolean): Maybe[T]
}

case object MaybeNot extends Maybe[Nothing] {
  def map[B](f: Nothing => B): Maybe[B] = MaybeNot
  def flatMap[B](f: Nothing => Maybe[B]): Maybe[B] = MaybeNot
  def filter(p: Nothing => Boolean): Maybe[Nothing] = MaybeNot
}

case class Just[+T](value: T) extends Maybe[T] {
  def map[B](f: T => B): Maybe[B] = Just(f(value))
  def flatMap[B](f: T => Maybe[B]): Maybe[B] = f(value)
  def filter(p: T => Boolean): Maybe[T] = {
    if (p(value)) this
    else MaybeNot
  }
}

object MaybeTest extends App {
  val just3 = Just(3)
  println(just3) // Just 3 means the single element is 3 
  println(just3.map(_ * 2)) // After the map, our single element is 6
  // This is interesting because Just(false) is valid. So the "holds 1 element"
  // restriction means a single element of any type, hence the use of generics. 
  println(just3.flatMap(x => Just(x % 2 == 0))) // After a flatMap, we find out the single value % 2 does not == 0
  println(just3.filter(_ % 2 == 0)) // We get a MaybeNot object 
}

