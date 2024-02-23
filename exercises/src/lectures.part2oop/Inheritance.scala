package lectures.part2oop

object Inheritance extends App {

  class Animal {
    // private, cannot be accessed
    // protected, can only be used in the class or subclasses
    // no modifier = public
    def eat = println("nomnomnom")
    // final makes it impossible to override a member
    //final eat = println("nomnomnom")
    val creatureType = "Wild Creature"
  }

  class Cat extends Animal {
    def crunch = {
      eat
      println("crunch crunch")
    }
  }

  val cat = new Cat
  cat.crunch

  // constructors
  class Person(name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }
  // Code does not compile since we need the constructor of Person still before
  // calling the constructor of Adult
  // class Adult(name: String, age: Int, idCard: String) extends Person
  class Adult(name: String, age: Int, idCard: String) extends Person(name, age)

  // If we add the "this" definition in Person, we can ommit age since we
  // provided a default value
  class Adult2(name: String, age: Int, idCard: String) extends Person(name)

  // Can override values in the constructor or in the body of the class as well
  class Dog(override val creatureType: String) extends Animal {
    // override val creatureType = "domestic"
    override def eat = {
      super.eat // eat in the super class
      println("crunch, crunch")
    }
  }

  val dog = new Dog("K9")
  dog.eat
  println(dog.creatureType)

  // type substitution (known as polymorphism)
  // If we have a collection of Animals and call the eat function, the
  // implementations in the subclasses will be called 
  val unknownAnimal: Animal = new Dog("K9")
  unknownAnimal.eat

  // preventing overrides
  // 1 - use final on a member
  // 2 - use final on the entire class
  // 3 - use sealed, limiting extension of class inside of the file only

}
