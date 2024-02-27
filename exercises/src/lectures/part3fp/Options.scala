package lectures.part3fp

object Options extends App {

  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None
  println(myFirstOption)
  println(noOption)

  // unsafe APIs
  def unsafeMethod(): String = null

  val badResult = Some(unsafeMethod()) // WRONG
  val correctResult = Option(unsafeMethod()) // CORRECT
  println(correctResult)

  /* The most important thing here is how to properly handle Option types
   */
  // Option type should be doing the null check for you

  // chained methods
  def backupMethod(): String = "A valid result"

  // This is how you work with unsafe APIs
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))

  // DESIGN unsafe APIs
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid result")

  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()

  // functions on Options
  println(myFirstOption.isEmpty) //
  println(myFirstOption.get) // UNSAFE - DO NOT USE THIS

  // map, flatMap, filter
  println(myFirstOption.map(_ * 2)) // Some(8)
  println(myFirstOption.filter(x => x > 10)) // None
  println(myFirstOption.flatMap(x => Option(x * 10))) // Some(40)

  // for-comprehensions

  /* 1. Assume you are given an API from other programmers
   */
  val config: Map[String, String] = Map(
    // fetched from elsewhere like a config file or some other place
    // values for host and port might or might not be here yet
    "host" -> "176.45.36.1",
    "port" -> "80"
  )

  class Connection {
    def connect = "Connected" // connect to some server
  }
  object Connection {
    val random = new scala.util.Random(System.nanoTime())
    def apply(host: String, port: String): Option[Connection] = {
      if (random.nextBoolean()) Some(new Connection)
      else None
    }
  }

  // try to establish a connection, if so - print the connect method

}
