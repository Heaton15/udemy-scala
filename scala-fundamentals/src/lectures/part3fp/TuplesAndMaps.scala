package lectures.part3fp

object TuplesAndMaps extends App {

  // tuples = finite ordered "lists"

  // Tuple2[Int, String] = (Int, String)
  val aTuple = new Tuple2(2, "hello, Scala")

  // Don't need the new Tuple part
  // Tuple2[Int, String] = (Int, String)
  val shorthandTuple = (2, "hello, Scala")

  println(aTuple._1) // print the first element of the tuple
  println(aTuple.copy(_2 = "goodbye Java")) // Replace the 2nd element
  println(aTuple.swap) // switch the elements

  // Maps keys -> values

  val aMap: Map[String, Int] = Map()
  // ("Jim", 555) == "Jim" -> 555
  val phonebook = Map("Jim" -> 555, "Daniel" -> 789).withDefaultValue(-1)
  println(phonebook)

  println(phonebook.contains("Jim")) // .contains returns Boolean
  println(phonebook("Jim")) // accesses the key value pair
  println(phonebook("Mary")) // Returns the default value since it doesn't exist

  val newPairing = "Mary" -> 678
  val newPhonebook = phonebook + newPairing
  println(newPhonebook)

  // functionals on maps
  // map, flatMap, filter

  // lowercases the keys
  println(phonebook.map(pair => pair._1.toLowerCase -> pair._2))

  // filterKeys
  println(phonebook.view.filterKeys(x => x.startsWith("J")).toMap)

  // mapValues
  println(phonebook.view.mapValues(numbers => numbers * 10).toMap)

  println(phonebook.toList)
  println(List("Daniel" -> 555).toMap)

  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  // groups by the group requirement , nice
  // frequently used
  println(names.groupBy(name => name.charAt(0)))

  /*
   * 1. What would happen if I had two original entries "Jim" -> 555 and "JIM"
   *    -> 900 and we ran the  println(phonebook.map(pair => pair._1.toLowerCase
   *    -> pair._2)) code
   *
   * 2. Overly simplified social network based on maps
   *  - Person = String
   *    - Add a person to the network
   *    - Remove a person from the network
   *    - friend (mutual adds another as a friend)
   *    - unfriend (mutual)
   *    - number of friends of a person
   *    - person with the most friends
   *    - how many people have NO friends
   *    - if there is a social connection between two people(direct or not)
   */

  // Task 1
  // It looks like the last seen version in the Map takes precedence
  // !!! Careful with mapping keys since you can wipe data! We might want that
  val overlapPhonebook = Map("JIM" -> 789, "Jim" -> 900)
  println(overlapPhonebook.map(pair => pair._1.toLowerCase -> pair._2))

  // Task 2, a quick peak shows this is all just functions, but it also seems
  // like a decent candidate for OOP.

  // Note a "Person" is just a string
  // We are managing a "Network" so we want to have a database of people and their connections
  // A Person adds to a network, so we want to add a Person to the network
  type Person = String

  // --------------------------------------------
  // MY SOLUTIONS TO THE TUPLES / MAPS EXERCISES
  // --------------------------------------------

  // Add people to the social network
  def addSolution(
      network: Map[String, List[String]],
      person: String
  ): Map[String, List[String]] = network + (person -> List())

  def add(
      network: Map[Person, List[Person]],
      newMember: Person
  ): Map[Person, List[Person]] = {
    val newNetwork = network ++ Map(newMember -> List.empty)
    newNetwork
  }

  // In my solution I treated it as removing the person from the network which
  // makes sense. But, if people were friends with that person, all of their
  // friends lists need to now be updated to no longer contain this person.
  //
  // I forgot to do this part, whoops.
  def removeSolution(
      network: Map[String, Set[String]],
      person: String
  ): Map[String, Set[String]] = {
    def removeAux(
        friends: Set[String],
        networkacc: Map[String, Set[String]]
    ): Map[String, Set[String]] = {
      if (friends.isEmpty) networkacc
      else
        removeAux(
          friends.tail,
          unfriendSolution(networkacc, person, friends.head)
        )
    }

    val unfriended = removeAux(network(person), network)
    unfriended - person
  }
  // Remove a person from the social network
  def remove(
      network: Map[Person, List[Person]],
      removeMember: Person
  ): Map[Person, List[Person]] = {
    // If the name doesn't exist, the conditional will filter it out for us. No
    // default value checking needed.
    val newNetwork = network.filter((name, conns) => name != removeMember)
    newNetwork
  }

  // Add a person to a Person's social network
  // Adding a friend means you both get added together. Forgot that there is a
  // handshake where A and B both become friends, not just A becomes friends
  // with B

  // Update List[String] to Set[String] if we want to keep unique friend values
  // and not have duplicates
  def friendSolution(
      network: Map[String, List[String]],
      a: String,
      b: String
  ): Map[String, List[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)

    // The most important thing here is that we can get static versions of the
    // friends and then just append to the network and it will override the old
    // network with the new friends and we will return the updated network
    network + (a -> (friendsA :+ b)) + (b -> (friendsB :+ a))
  }

  def friend(
      network: Map[Person, List[Person]],
      initiator: Person,
      target: Person
  ): Map[Person, List[Person]] = {
    val newNetwork = network
      .filter((name, conns) => name == initiator)
      .view
      .mapValues(newFriend => newFriend :+ target)
      .toMap
    network ++ newNetwork
  }

  def unfriendSolution(
      network: Map[String, Set[String]],
      a: String,
      b: String
  ): Map[String, Set[String]] = {
    val friendsA = network(a)
    val friendsB = network(b)
    // You have to use Sets to have access to the - operator to remove from the Set
    network + (a -> (friendsA - b)) + (b -> (friendsB - a))
  }

  // Remove a person from a person's connections
  def unfriend(
      network: Map[Person, List[Person]],
      initiator: Person,
      target: Person
  ): Map[Person, List[Person]] = {
    // val newNetwork = network.filter((name, conns) => name == initiator).view.mapValues(name => name.filter(i => i != target)).toMap
    // Example for-comprehension that reduces the combination of filters and
    // mapValues

    // Clearly the provided solution is much cleaner than this garbage.
    val newNetwork = for {
      (name, conn) <- network if name == initiator
      newConns = for (i <- conn if i != target) yield i
    } yield (name, newConns)
    network ++ newNetwork
  }

  // Solution was correct
  def numFriends(network: Map[Person, List[Person]], person: Person): Int = {
    if (!network.contains(person)) 0
    else network(person).size
  }

  def mostFriendsSolution(network: Map[String, Set[String]]): String = {
    network.maxBy(pair => pair._2.size)._1
  }

  // Technically, multiple people could have the same amount of friends
  // The maxBy is the command you were looking for. This let's you apply a
  // function that determines what gets returned.
  def mostFriends(network: Map[Person, List[Person]]): List[Person] = {
    val mostFriends: Int =
      network.map((name, friends) => name -> friends.size).values.max
    val friendsWithMostFriends: List[Person] = network
      .filter((person, friends) => friends.size == mostFriends)
      .keys
      .toList
    friendsWithMostFriends
  }

  // Solution was sort of similar. Probably could use filterKeys instead of .keys
  def noFriendsSolution(network: Map[String, Set[String]]): Int = {
    network.view.filterKeys(k => network(k).size == 0).size
    // Another solution
    network.count(pair => pair._2.isEmpty)
  }

  def noFriends(network: Map[Person, List[Person]]): List[Person] = {
    val hasNoFriends: List[Person] =
      network.filter((person, friends) => friends.size == 0).keys.toList
    hasNoFriends
  }

  def hasSocialConnectionSolution(
      network: Map[String, Set[String]],
      a: String,
      b: String
  ): Boolean = {
    // Nice tail recursive function
    def bfs(
        target: String,
        consideredPeople: Set[String],
        discoverdPeople: Set[String]
    ): Boolean = {
      if (discoverdPeople.isEmpty) false
      else {
        val person = discoverdPeople.head
        if (person == target) true
        else if (consideredPeople.contains(person))
          // if we already encountered the person, we don't need their network
          // or the person again
          bfs(target, consideredPeople, discoverdPeople.tail)
        else
          bfs(
            target,
            consideredPeople + person,
            discoverdPeople.tail ++ network(person)
          )
      }
    }
    bfs(b, Set(), network(a) + a)
  }

  // If 2 people share a connection, there exists a social connection
  // This was directly friendships only, we needed to check friends of friends
  // as well
  def hasSocialConnection(
      network: Map[Person, List[Person]],
      person1: Person,
      person2: Person
  ): Boolean = {
    // See if any persons in the person1Friends list matches anything in the
    // person2 friends list
    val hasConnections =
      if (
        network(person1)
          .flatMap(friend =>
            network(person2).filter(friend2 => friend == friend2)
          )
          .size > 0
      ) true
      else false
    hasConnections
  }

  // *    - if there is a social connection between two people(direct or not)

  val socialNetwork: Map[Person, List[Person]] = Map()
  val a = add(socialNetwork, "Tim"); println(a)
  val b = add(a, "Tyler"); println(b)

  val c = friend(b, "Tim", "John"); println(c)
  val d = friend(c, "Tim", "Jason"); println(d)
  println(numFriends(c, "Tim"))
  val e = unfriend(d, "Tim", "Tyler"); println(e)
  val f = mostFriends(e)
  println(f)
  val g = friend(e, "Tyler", "John")
  println(g)
  println(hasSocialConnection(g, "Tim", "Tyler"))

}
