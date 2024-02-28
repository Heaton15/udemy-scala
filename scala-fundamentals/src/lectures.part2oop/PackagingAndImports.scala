package lectures.part2oop

// import playground.Cinderella

object PackagingAndImports extends App {

  // package members are accessible to everything that is a part of the package
  val writer = new Writer("Daniel", "RockTheJVM", 2018)

  // import the package
  // val princess = new playground.Cinderella // playground.Cinderella = fully qualified name
  
  // packages are in hierarchy
  // part2oop is a sub-package of lectures, and this should follow the file structure

  // package object 
  // This was created because sometimes we want constants and stuff packaged up
  
  // default imports
  // java.lang - String, Object, Exception
  // scala - Int, Nothing, Function
  // scala.Predef = println, ???
}
