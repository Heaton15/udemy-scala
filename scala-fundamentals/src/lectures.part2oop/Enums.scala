package lectures.part2oop

// Scala 3 now has first class support for Enums
enum Permissions {
  case READ, WRITE, EXECUTE, NONE

  // add fields / methods

  def openDocument(): Unit = {
    if (this == READ) println("opening document")
    else println("Reading not allowed")
  }
}

enum PermissionsWithBits(bits: Int) {
  case READ extends PermissionsWithBits(4) // 100
  case WRITE extends PermissionsWithBits(2) // 010
  case EXECUTE extends PermissionsWithBits(1) // 001
  case NONE extends PermissionsWithBits(0) // 000
}

object PermissionsWithBits {
  def fromBits(bits: Int): PermissionsWithBits =
    PermissionsWithBits.NONE
}

object Enums extends App {

  val somePermissions: Permissions = Permissions.READ
  somePermissions.openDocument()

  // standard API with Enums
  val somePermissionsOrdinal = somePermissions.ordinal // 0 since READ was first 
  val allPermissions = PermissionsWithBits.values // array o all possible values
  val readPermission: Permissions = Permissions.valueOf("READ") // matched to one of the names of the ENUM


}
