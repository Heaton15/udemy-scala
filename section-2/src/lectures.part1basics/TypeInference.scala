package lectures.part1basics

object TypeInference extends App {

  val message = "Hello, world!"
  // The compiler knows to create val message: String = "Hello, world!" because
  // it can infer the type

  // The compiler knows to return an Int since we sum 1 to an Int
  def succ(x: Int) = x + 1
  // -> Creates behind the scenes def succ(x: Int): Int = x + 1
  
  // The compiler can struggle to find the return type sometimes
  // Recursive calls require you to specify the return type
  def factorial(n: Int): Int = 
    if (n <= 0) 1
    else n * factorial(n-1)
}
