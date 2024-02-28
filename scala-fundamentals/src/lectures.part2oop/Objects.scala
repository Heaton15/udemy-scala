package lectures.part2oop

object Objects extends App {

  // This can be used inside the primary object instead of extending App
  // def main(args: Array[String]): Unit = {} 
  
  // SCALA DOES NOT HAVE CLASS-LEVEL FUNCTIONALITY, no "static"

  // objects can have this static-like functionality and is the required use
  // case in the Scala language 

  object Person { // type + its only instance
    // "static/class" - level functionality
    val N_EYES = 2
    def canFly: Boolean = false

    // factory method. Purpose is to build Person's 
    // Typically, you see the apply() function used to create these
    //def from(mother: Person, father: Person): Person = new Person("Bobby")
    // This is heavily used in scala practice
    def apply(mother: Person, father: Person): Person = new Person("Bobby")

  }

  class Person(val name: String) {
    // instance-level functionality 
  }
  // COMPANIONS

  println(Person.N_EYES)
  println(Person.canFly)

  // Scala objects = SINGLETON INSTANCE 
  val mary = Person
  val john = Person
  // This is true because only 1 instance of Person can exist, so mary and john
  // will be identical. Singleton instances will be identical by implementation
  println(mary == john)

  val classMary = new Person("Mary")
  val classJohn = new Person("John")
  // This prints false because we have unique instances instead of the singleton
  // object 
  println(classMary == classJohn)
  val bobby = Person(classMary, classJohn)

  // Scala Applications = Scal object with a very special method called 
  // def main(args: Array[String]): Unit (extends App) provides this
  // implementation
   
  
}
