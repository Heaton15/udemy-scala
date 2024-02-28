package lectures.part3fp

import scala.util.Failure
import scala.util.Success
import scala.util.Try
import scala.util.Random

object HandlingFailures extends App {

  // We learned about try / catch in earlier lectures, but now we are going to
  // look at the Try[] wrapper that helps determine if a computation might fail
  // or not.

  // Results can be Failure(throwable) or Success(value)

  // create success and failure explicitly

  val aSuccess = Success(3)
  val aFailure = Failure(new RuntimeException("SUPER FAILURE"))
  println(aSuccess)
  println(aFailure)

  // Try companion object normally handles the Success vs Failure results
  def unsafeMethod(): String = throw new RuntimeException(
    "NO STRING FOR YOU BUSTER"
  )

  // Try objects via the apply method
  // We caught the failure without crashing and not using try / catch
  val potentialFailure = Try(unsafeMethod())
  println(potentialFailure)

  // syntax sugar
  val anotherPotentialFailure = Try {
    // code that might throw 
  }
  
  // utilities
  println(potentialFailure.isSuccess)

  // orElse
  def backupMethod(): String = "A valid result"
  val fallbackTry = Try(unsafeMethod()) orElse Try(backupMethod())
  println(fallbackTry)

  // If you design the API, if you know your code might throw an exception wrap
  // it in a Try

  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException)
  def betterBackupMethod(): Try[String] = Success("A valid result")
  val betterFallback = betterUnsafeMethod() orElse betterBackupMethod()

  // map, flatMap, filter
  println(aSuccess.map(_ * 2))
  println(aSuccess.flatMap(x => Success(x * 10)))
  println(aSuccess.filter(_ > 10)) // This will cause a Failure because althought succesful the predicate fails 

  // => for-comprehensions

  /* Exercise
   */
  val hostname = "localhost"
  val port = "8080"
  def renderHTML(page: String) = println(page)

  class Connection{
    def get(url: String): String = {
      val random = new Random(System.nanoTime())
      if (random.nextBoolean()) "<html>...</html>"
      else throw new RuntimeException("Connection interrupted")
    }

    def getSafe(url: String): Try[String] = Try(get(url))
  }

  object HttpService {
      val random = new Random(System.nanoTime())
      def getConnection(host: String, port: String): Connection = {
        if (random.nextBoolean()) new Connection
        else throw new RuntimeException("Someone else took the port")
      }

      def getSafeConnection(host: String, port: String): Try[Connection] = Try(getConnection(host, port))
  }

  // if you get the html page from the connection, print it to the console. call renderHTML

  //renderHTML(Connection.get("garbage"))
  
  // 1. getConnection so that we have something to connect to. This requires an open port
  // 2. Once we have a Connection, call get on it and print if we get the html back other 

  // Attempt to get a connection. We didn't do the flatMap he did but thats
  // because we didn't care to store the Connection value in the solution.
  // Otherwise, looked good. 
  val newConnection = Try(HttpService.getConnection(hostname, port))
  newConnection.foreach(i => renderHTML(i.get("garbage url")))

  // His solution
  val possibleConnection = HttpService.getSafeConnection(hostname, port)
  val possibleHTML = possibleConnection.flatMap(connection => connection.getSafe("garbage"))
  possibleHTML.foreach(renderHTML)
  
  // for-comprehension version
  
  for {
    connection <- HttpService.getSafeConnection(hostname, port)
    html <- connection.getSafe("garbage")
  } yield renderHTML(html)



  


}
