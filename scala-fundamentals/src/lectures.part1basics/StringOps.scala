package lectures.part1basics

object StringOps extends App {

  val str: String = "Hello, I am learning Scala"
  println(str.charAt(2))
  println(str.substring(7, 11))
  println(str.split(" ").toList)
  println(str.startsWith("Hello"))
  println(str.replace(" ", "-"))
  println(str.toLowerCase())
  println(str.toUpperCase())
  println(str.length)

  val aNumberString = "45"
  val aNumber = aNumberString.toInt
  println('a' +: aNumberString :+ 'z')
  println(str.reverse)
  println(str.take(2))
  
  // Scala-specific: String interpolators
  val name = "David"
  val age = 12
  val greeting = s"My name is $name and I am $age years old"
  val anoterGreeting = s"Hello, my name is $name and I will be turning ${age + 1} years old"

  // F-interpolators, can also receive printf like formats
  // The F-interpolators can also check for type correctness
  val speed = 1.2f
  val myth = f"David can eat $speed%2.2f burgers per minute"
  println(myth)

  // raw-interpolator, pring characters literally
  // This will not escape the backslash 
  println(raw"This is a \n newline")

  val escaped = "This is a \n newline"

  // This will escape them because the original string was not raw
  println(raw"$escaped")





}
