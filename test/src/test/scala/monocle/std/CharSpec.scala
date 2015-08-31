package monocle.std

import cats.std.boolean._
import monocle.MonocleSuite
import monocle.function._
import monocle.law.discipline.{OptionalTests, PrismTests}

class CharSpec extends MonocleSuite {

  checkAll("Char index bit", OptionalTests(index[Char, Int, Boolean](0)))

  checkAll("Char to Boolean ", PrismTests(charToBoolean))

}
