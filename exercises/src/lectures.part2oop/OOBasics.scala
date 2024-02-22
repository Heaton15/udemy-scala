package lectures.part2oop

object OOBasics extends App {

  val person = new Person("John", 26)

  // We should see a weird string representation of the class
  println(person.age)
  person.greet("Daniel")
  person.greet()

  // author != imposter even though their parameters are identical
  val author = new Writer("Charles", "Dickens", 1812)
  val imposter = new Writer("Charles", "Dickens", 1812)

  val novel = new Novel("Great Expectations", 1861, author)
  println(novel.authorAge)
  println(novel.isWrittenBy(author))

}

// Adding val before the parameters makes it an accessible field in the class
// constructor

// class parameters are NOT FIELDS. You cannot access the parameters unless you
// add val before the parameters.
class Person(val name: String, val age: Int = 0) {
  // body of the class
  val x = 2 // field of Person
  println(1 + 3)

  // method because it is inside a class
  // this is used to access the name parameter of class person and not the
  // method's parameter

  // overloading means defining methods with the same name but different
  // signatures
  def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")
  // There is an implicit this.name with this call because there is not method
  // parameter called name
  def greet(): Unit = println(s"Hi, I am $name")

  // multiple constructors
  // This auxillary constructor might be annoying and can be fixed by addding a
  // default argument to the class parameter
  // Because we have a default parameter, we can just create a new person and
  // skip defining this auxillary constructor
  def this(name: String) = this(name, 0)
  def this() = this("John Doe")
}

/* Novel and Writer class
 * Writer: first name, surname, year of birth
 *  - method fullName returns cat of first name and surname
 *
 * Novel: name, year of release, author
 *  - authorAge, age of author at the year of release
 *  - isWrittenBy(author)
 *  -copy, receives a new year of release and returns a new instance of Novel
 *  with a new YOR
 */

class Writer(firstName: String, surName: String, val year: Int) {
  def fullName(): String = s"${this.firstName} ${this.surName}"
}

class Novel(name: String, yor: Int, author: Writer) {
  def authorAge: Int = this.yor - this.author.year
  def isWrittenBy(author: Writer) = author == this.author
  def copy(newYor: Int) = new Novel(this.name, newYor, this.author)
}

/* Counter class
 *  - receives an int value
 *  - method which returns current count
 *  - method to increment / decrement the counter by 1 but should return a new
 *    Counter
 *  - overload the inc/dev to receive and amount to inc/dec by
 */

class Counter(value: Int) {
  def currentCount: Int = this.value
  // Instances will be fixed and cannot be modified in functional programming.
  // This is an incredibly important concept
  def inc(): Counter = new Counter(
    this.currentCount + 1
  ) // immutability and is the same principle as using vals for everything

  // The most important thing out of this is that we are calling the counter
  // class from the returned class to create a new class again. 
  def inc(incVal: Int): Counter = {
    if (incVal <= 0) this
    else inc().inc(incVal - 1)
  }

  def dec(): Counter = new Counter(this.currentCount - 1)
  def dec(decVal: Int): Counter = {
    if (decVal <= 0) this
    else dec().dec(decVal - 1)
  }
}
