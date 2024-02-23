package lectures.part2oop

object AbstractDataTypes extends App {
  // abstract fields are blank or un-implemented fields

  abstract class Animal {
    val creatureType: String
    def eat: Unit
  }

  // This fails since the abstract class needs extended and implemented
  // val animal = new Animal

  class Dog extends Animal {
    val creatureType: String = "Canine"
    def eat: Unit = println("crunch crunch")
  }

  // traits

  trait Carnivore {
    def eat(animal: Animal): Unit
    // non-abstract member
    val preferredMeal: String = "fresh meat"
  }

  trait ColdBlooded 

  class Crocodile extends Animal with Carnivore with ColdBlooded {
    val creatureType: String = "croc"
    def eat: Unit = println("nomnomnom")
    def eat(animal: Animal): Unit = println(s"I'm a croc and I'm eating ${animal.creatureType}")
  }

  val dog = new Dog
  val croc = new Crocodile
  croc.eat(dog)

  // Traits vs Abstract Classes

  // 1. Both can have abstract and non-abstract members
  // 2. Traits cannot have constructor parameters
  // 3. You can only extend 1 class but you can extend multiple traits
  // 4. traits are behavior, abstract class is a "thing" (how to help think
  //    about when to use one over the other)

  
}
