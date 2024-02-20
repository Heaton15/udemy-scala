package lectures.part1basics

object Expressions extends App {

  val x: Int = 1 + 2 // EXPRESSION which resolves to Int
  println(x)

  println(2 + 3 * 4)
  // + - * / & | ^ << >> >>> (right shift with zero extension)

  println(1 == x) // false
  // ==, !=, >, >=, <, <=

  println(!(1 == x)) // true
  // ! && ||

  var aVariable = 2
  aVariable += 3
  println(aVariable) // 5
  aVariable -= 3
  println(aVariable) // 2

  // Instructions (DO) vs Expressions (VALUE)

  // IF expression
  val aCondition = true
  val aConditionedValue = if (aCondition) 5 else 3
  println(aConditionedValue)
  println(if(aCondition) 5 else 3)

  var i = 0
  while (i < 10) {
    println(i)
    i += 1
  }

  // NEVER WRITE THIS AGAIN. Of course of course
  // This function operation could be a better example of printing 0 to 10
  (0 to 10).foreach{case i => println(i)}

  val aWeirdValue = (aVariable = 3) // Unit === void in other language
  println(aWeirdValue)


  // side effects: println(), whiles, reassigning of vars which all return Unit

  // Code blocks

  // This code block is an EXPRESSION which is going to resolve to something
  // The last expression is what the code block returns
  val aCodeblock = {
    val y = 2
    val z = y + 1
    if (z > 2) "hello" else "goodbye"
  }

  // z cannot be found here because it belongs in the code block scope
  // val anotherValue = z + 1
  
  // 1. What is the difference between "hello world" vs println("hello world")?
  //  - string vs expression
  // 2.
  val someValue = {
    2 < 3
  } // returns true
  val someOtherValue = {
    if (someValue) 239 else 986
    42
  } // returns 42
}
