package lectures.part2oop

object MethodNotations extends App {

  // Needs to be in the object since it will clash with the Person class in the
  // OOPBasics 
  class Person(val name: String, favoriteMovie: String, val age: Int = 0) {
    def likes(movie: String): Boolean = favoriteMovie == movie
    def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"

    // Task 1
    def +(nickName: String): Person = new Person(s"${this.name} (${nickName})", this.favoriteMovie)

    // Task 2
    def unary_+ : Person = new Person(this.name, this.favoriteMovie, age+1)

    // Task 3
    def learns(lang: String): String = this.name + " learns " + lang
    def learnsScala = this learns "Scala"

    // Task 4
    def apply(times: Int): String = s"${this.name} watched ${this.favoriteMovie} ${times} times"

    def unary_! : String = s"$name, what the heck?!"
    def isAlive: Boolean = true
    def apply(): String = s"Hi, my name is $name and I like $favoriteMovie"
  }

  val mary = new Person("Mary", "Inception")
  // The 2 calls are equivalent because the syntactic sugar of infix notation
  // This only works with methods that have just one parameter 
  println(mary.likes("Inception"))
  println(mary likes "Inception")
  // infix notation = operator notation

  // "operators" in Scala
  val tom = new Person("Tom", "Fight Club")
  // You can use odd characters to name your methods however you want. +, #, &
  // are reserved in other languages but not in Scala!
  println(mary + tom)
  println(mary.+(tom))

  println(1 + 2) // infix notation!
  println(1.+(2)) // Funny how this works right?
  // Akka actors have ! ? 

  // prefix notation
  val x = -1 // unary operator, equivalent to unary_-
  val y = 1.unary_- // unary_ prefix creates the - for the infix notation

  // the def unary_! definition of person causes this prefix to cause a string
  // to be returned. Nice
  println(!mary)

  // postfix notation
  println(mary.isAlive)
  // In the training this is compilable but it looks like in Scala 3 they are
  // moving towards deprecating this kind of notation. 
  // println(mary isAlive)

  // apply method is a special method we will use frequently
  // These are equivalent, and the apply method knows that the class can be
  // called and automatically call the apply function
  // Not sure how useful this is, but it will be great with companion objects
  println(mary.apply())
  println(mary())

  /* 1. Overload the + operator and add another + operator which receives a
   *    string and returns a new Person with a nick name. mary + "the rockstar"
   *    => mary (the rockstart) with the same favorite movie
   *
   * 2. Add an age to the Person class with default 0.
   *  - Add a unary + operator => new Person with the age + 1
   *  - +mary => mary with the age incrementer
   *
   * 3. Add a "learns" method in the Person class => "Mary learns Scala"
   *  - Add a learnsScala method, calls learns method with "Ccala"
   *      - Use it in postfix notati0on
   *
   * 4. Overload the apply method
   *  - Receive number and print a string
   *  - mary.apply(2) => "Mary watched Inception 2 times"
   */

  // Task 1 works!
  println((mary + "the Rockstar")())

  // Task 2 works!
  println((+mary).age)

  // Task 3 works!
  // Note that postfix looks to not work in newer Scala now
  //println(mary learnsScala)

  // Task 4
  println(mary(10))

}
