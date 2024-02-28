package lectures.part1basics

object ValuesVariablesTypes extends App {
  // vals are immutable
  val x: Int = 42
  println(x)

  val xx: Double = 42
  // x is Int and xx is Double because compiler can infer types

  // semi-colons can break lines but is generally discouraged
  val aString: String = "hello"; val bString: String = "goodbye"

  val aBoolean: Boolean = true // also can false
  val aChar: Char = 'a'
  val anInt: Int = 5
  val aShort: Short = 4613 // uses 2-bytes instead of 4
  // Note the L at the end of the long
  val aLong: Long = 461321421232543421L // integer types with 8-bytes instead of 4
  // f is required to make it a float and not a double
  val aFloat: Float = 2.0f
  val aDouble: Double = 2.0

  // variables (generally discouraged in scala)
  var aVariable: Int = 4
  aVariable = 5 // side effects 
}
     

