package lectures.part3fp

object WhatsAFunction extends App {

  // DREAM: use functions as first class elements
  // problem: Most come from a OOP world, thinking of instances of classes

  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(2))

  // function types = Function[A, B]
  // These function types can support up to 22 parameters
  // Function[Int, Int ... Int] <- up to 22 times
  val stringToIntConverter = new Function[String, Int] {
    override def apply(string: String): Int = string.toInt
  }

  println(stringToIntConverter("3") + 4)

  // In place of Function[Int, Int, Int] , Note the last element is the return
  // type
  val adder: (Int, Int) => Int = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }

  // Function types Function2[A, B, R] === (A, B) => R

  // ALL SCALA FUNCTIONS ARE OBJECTS

  /* 1. A function which takes 2 strings and concatenates them (Function2)
   * 2. Go to the MyList implementation and transform MyPredicate and
   *    MyTransformer into function types.
   * 3. Define a function which takes an argument Int and returns another
   *    function which takes an Int and returns an Int
   *    - What's the type of this function
   *    - How do you do it?
   */

  // Task 1
  def catStrings: (String, String) => String =
    new Function2[String, String, String] {
      override def apply(a: String, b: String): String = a + b
    }

  // Task 2 (in the MyList file we have been updating constantly)

  // Task 3
  val superAdder = new Function1[Int, Function1[Int, Int]] {
    override def apply(a: Int): Function1[Int, Int] = new Function1[Int, Int] {
      override def apply(b: Int): Int = a + b
    }
  }

  val adder3 = superAdder(3) // Creates a function which adds 3
  println(adder3(4)) // adds 3 to 4
  println(superAdder(3)(4)) // same thing as creating an intermediate val

  // We have now seen currying in action woohoo!
  // This is called a curried function!!!

  // We are now going to look at higher order functions and curried functions
  // We want to start using functions as first class parameters

}

trait MyFunction[A, B] {
  def apply(element: A): B = ???
}
