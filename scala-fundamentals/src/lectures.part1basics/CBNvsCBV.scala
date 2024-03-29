package lectures.part1basics

object CBNvsCBV extends App {

  def calledByValue(x: Long): Unit = {
    println("by value: " + x)
    println("by value: " + x)
  }

  // Called by name instead of by value
  def calledByName(x: => Long): Unit = {
    println("by value: " + x)
    println("by value: " + x)
  }

  // In call by value, the System.nanoTime() function will be resolved to a
  // value, and that value will be passed into the calledByValue(...) function
  // which can use the resultant.
  println("Call by Value")
  calledByValue(System.nanoTime())

  // In call by name, the System.nanoTime() call is itself passed into the
  // calledByName(...) function and is ONLY resolved when it is accessed.
  // Extremely useful in LazyStreams and in things that might fail
  println("Call by Name")
  calledByName(System.nanoTime())

  def infinite(): Int = 1 + infinite()
  def printFirst(x: Int, y: => Int) = println(x)

  // This should cause a StackOverflow error
  // printFirst(infinite(), 34)

  // By Name delays the infinite() call, so we never actually evaluate it
  printFirst(34, infinite())
}
