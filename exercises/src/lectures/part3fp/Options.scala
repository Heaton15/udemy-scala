package lectures.part3fp

object Options extends App {

  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None
  println(myFirstOption)
  println(noOption)

  // .get
  val myMap: Map[String, String] = Map("a" -> "b")
  val myVal =
    myMap.get("a") // Option[String], this automatically returns the Option type

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

  // supports for-comprehensions

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

  /* Assumptions
   * 1. "host" or "port" can not be initialized, so assume he means they are null
   *  - We probably want to wrap them in Option to be able to check if they are
   *    null then
   * 2.
   */

  // Fetch the config information

  // Solution
  val host = config.get("host")
  val port = config.get("port")

  // Annotated, this is what happens
  // if (h != null)
  //  if (p != null)
  //    return Connection.apply(h, p)
  // else
  //  return null
  //
  // Pay attention that a flatMap, map, etc on a Option will not run if its None
  val connection = host.flatMap(h => port.flatMap(p => Connection(h, p)))
  val connectionStatus = connection.map(c => c.connect)
  println(connectionStatus)
  connectionStatus.foreach(println)

  config
    .get("host")
    .flatMap(host =>
      config
        .get("port")
        .flatMap(port => Connection(host, port))
        .map(connection => connection.connect)
    )
    .foreach(println)

  // for-comprehensions

  val forConnectionStatus = for {
    // given a host obtained from config.get(...)
    host <- config.get("host")
    // given a port obtrained from config.get(...)
    port <- config.get("port")
    // give a connection obtained from Connection(host, port)
    connection <- Connection(host, port)
    // give me the connection status
  } yield connection.connect
}
