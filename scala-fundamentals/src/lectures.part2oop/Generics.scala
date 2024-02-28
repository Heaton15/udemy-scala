package lectures.part2oop

object Generics extends App {
  // In the inheritance videos we created a linked list for only Integers. What
  // about for all kinds of data types? String, Int, Seq[List]?

  // Called a generic class
  class MyList[+A] {
    // use the Type A inside thje class definition
    
    // This does not compile because adding a Dog to a list of Cats makes it a list of Animals
    //def add(element: A): MyList[A] = ???
    
    //  This will compile because we are saying this
    
    /* 
     * A = Cat
     * B = Animal
     */
    // This translates that B must be a supertype of A, aka Animal must be
    // supertype of Cat. The function then accepts Animals and returns animals. 
    def add[B >: A](element: B): MyList[B] = ???
  }

  // 2 generic parameters
  class MyMap[Key, Value]

  // Also works on traits
  trait MyListAgain[A]

  val listOfIntegers = new MyList[Int]
  val listOfStrings = new MyList[String]

  // generic methods
  object MyList {
    def empty[A]: MyList[A] = new MyList[A]
  }

  val emptyListOfIntegers = MyList.empty[Int]

  // variance problem
  class Animal
  class Cat extends Animal
  class Dog extends Animal

  // We now have to pose some interesting questions, such as...
  // Q: If Cat extends Animal, does a List[Cat] extend a List[Animal]?
  //  - There are 3 answcat ers to this
  //  A1: Yes, List[Cat] extends List[Animal] - COVARIANCE
  //    - A list of Animals with list of Cats is okay
  //  A2: No, List[Cat] and List[Animal] are 2 separate things - INVARIANCE
  //    - A list of cats and list of animals are different
  //  A3: Hell, no - CONTRAVARIANCE
  //    - A list of Cats with list of Animals is okay

  // 1. COVARIANT LIST
  class CovariantList[+A]
  val animal: Animal = new Cat
  val animalList: CovariantList[Animal] = new CovariantList[Cat]
  // animalList.add(new Dog), is this allowed ??? HARD QUESTION to answer.
  // Conclusion is that we return a list of animals

  // 2. INVARIANT LIST
  class InvariantList[A]
  // This will error out!
  // val invariantAnimalList: InvariantList[Animal] = new InvariantList[Cat]

  // 3. CONTRAVARIANT LIST
  class ContravariantList[-A]
  val contravariantList: ContravariantList[Cat] = new ContravariantList[Animal]

  class Trainer[-A]
  class CovariantTrainer[+A]
  // This is nice, because a trainer of animals can train dogs, cats, dinosaurs,
  val trainer: Trainer[Cat] = new Trainer[Animal]

  // In the covariant case, we might not want our trainer of animals to only be
  // able to train just cats
  val covariantTrainer: CovariantTrainer[Animal] = new CovariantTrainer[Cat]

  // bounded types
  // All A must be subtype of animal
  class Cage[A <: Animal](animal: A)
  // All A must be supertype of animal
  class Cage2[A >: Animal](animal: A)
  val cage = new Cage(new Dog)
  class Car
  // Car is not an Animal so this will not work
  // val newCage = new Cage(new Car)


  // expand MyList to be generic


}
