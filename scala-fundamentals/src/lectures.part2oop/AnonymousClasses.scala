package lectures.part2oop

object AnonymousClasses extends App {

  abstract class Animal {
    def eat: Unit
  }

  // You would think this is not allowed since the class is abstract
  // But in fact, the class definition inside of new Animal {} 
  // makes the class an anonymous class. 
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("haha")
  }


  // This is created and substituted in place of the abstract class definition
  //class AnonymousClasses$$anon$1 extends Animal {
  //  override def eat: Unit = println("haha")
  //}

  println(funnyAnimal.eat)

  class Person(name: String) {
    def sayHi: Unit = println(s"Hi, my name is $name, how can I help?")
  }


  val jim = new Person("Jim") {
    override def sayHi: Unit = println(s"Hi, my name is Jim, how can I be of service?")
  }

  // Anonymous classes work for both abstract and non-abstract datatypes
  
}
