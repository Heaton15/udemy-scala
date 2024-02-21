package lectures.part1basics

object DefaultArgs extends App {

  def trFact(n: Int, acc: Int): Int = {
    if (n <= 1) acc
    else trFact(n-1, n*acc)
  }

  // We don't need to pass the 1 to the function if that is the same initial
  // value 99% of the time since it pollutes the function signature. 
  val fact10 = trFact(10, 1)

  // This is called a default argument
  def trFactNew(n: Int, acc: Int = 1): Int = {
    if (n <= 1) acc
    else trFact(n-1, n*acc)
  }

  val fact10New = trFactNew(10)

  def savePicture(format: String = "jpg", width: Int, height: Int): Unit = {
    println("saving picture")
  }

  // Let's assume that that "jpg" is called 99% of the time
  savePicture("jpg", 800, 600)

  // Without specifying the function arguments, you can't pass the 2nd and 3rd
  // argument. 
  
  // As a result, you need to explicitly specify the function arugments for this
  // to compile correctly
  savePicture(width=800, height=600)
}
