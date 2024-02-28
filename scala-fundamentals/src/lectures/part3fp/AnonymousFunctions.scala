package lectures.part3fp

object AnonymousFunctions extends App {

  // Still an OOP way to define an anonymous functions
  val oopDoubler = new Function1[Int, Int] {
    override def apply(x: Int) = x * 2
  }

  // This is the syntactic sugar for all that crap above
  // anonymous function / lambda
  //
  //
  // |||
  // vvv This code can be reduced to below
  // val doubler: Int => Int = (x: Int) => x * 2
  val doubler: Int => Int = x => x * 2

  // multiple params in a lambda must be in ()
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b

  // no params
  val justDoSomething: () => Int = () => 3
  println(justDoSomething) // function itself
  println(justDoSomething()) // lambdas must be called with parantheses

  // curly brances with lambdas, common style but not always loved 

  val stringToInt = { (str: String) =>
    str.toInt
  }

  // MOAR syntactic sugar
  // The _ notation is contextual, so the function definitions must be fully
  // defined 

  // val niceIncrementer: Int => Int = (x: Int) => x + 1
  val niceIncrementer: Int => Int = _ + 1

  // val niceAdder: (Int, Int) => Int = (a, b) => a + b
  val niceAdder: (Int, Int) => Int = _ + _ 

  /* 1. Go to MyList, replace all FunctionX calls with lambdas
   * 2. Rewrite the "special" adder that we made in the previous video (curried one) as an anon function
   */

  // Task 2
  // Yay! We created a curried function 
  val superAdder: Int => (Int => Int) = (a: Int) => (b: Int) => a + b

  
  // val superAdder = new Function1[Int, Function1[Int, Int]] {
  //   override def apply(a: Int): Function1[Int, Int] = new Function1[Int, Int] {
  //     override def apply(b: Int): Int = a + b
  //   }
  // }



}
