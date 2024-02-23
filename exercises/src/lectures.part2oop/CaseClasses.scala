package lectures.part2oop

object CaseClasses extends App {
  /*
   * equals, hashCode, toString get re-implemented a lot in classes
   * case classes are useful short hand as a light weight data holding class
   */

  case class Person(name: String, age: Int)

  // 1. Class parameters are automatically promoted to fields
  // 2. Sensible toString (pretty decent looking default)
  // 3. equals and hashCode implemented out of the box 
  // 4. Case Classes have handy copy method
  // 5. Case Classes have companion objects
  // 6. Case Classes are serializable
  // 7. Case Classes have extractor patterns = can be used in PATTERN MATCHING
  
  val jim = new Person("Jim", 34)
  println(jim.name)
  println(jim.toString)

  val jim2 = Person("Jim", 34)
  // true! If these were just classes, it would have been false. But the
  // "equals" method that is implemented compares the fields by default. 
  println(jim == jim2)

  // jim3 now contains the named parameters of jim
  val jim3 = jim.copy()

  // 5
  val thePerson = Person
  val mary = Person("Mary", 23) // <-- created from the default apply method

  // 6
  // Akka sends serializable data over a network frequently

  // 7 
  
  // Don't get companion objects
  case object UnitedKingdom {
    def name: String = "The UK of GB and NI"
  }


}
