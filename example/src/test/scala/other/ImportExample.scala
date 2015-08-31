package other

import monocle.MonocleSuite
import shapeless.test.illTyped
import shapeless.{::, HNil}

import scalaz.IList

case class Custom(value: Int)

object Custom {

  import monocle.Lens
  import monocle.function.Field1

  implicit val customHead = new Field1[Custom, Int]{
    def first = Lens((_: Custom).value)(v => c => c.copy(value = v))
  }
}

class ImportExample extends MonocleSuite {

  test("monocle.function._ imports all polymorphic optics in the scope") {
    import monocle.function._

    // do not compile because Each instance for List is not in the scope
    illTyped { """each[List[Int], Int].modify(List(1,2,3), _ + 1)""" }

    import monocle.std.list._
    each[List[Int], Int].modify(_ + 1)(List(1,2,3)) shouldEqual List(2,3,4)

    // also compile because Head instance for Custom is in the companion of Custom
    first[Custom, Int].modify(_ + 1)(Custom(1)) shouldEqual Custom(2)
  }

  test("monocle.syntax._ permits to use optics as operator which improves type inference") {
    import monocle.function._
    import monocle.std.list._

    // do not compile because scala cannot infer which instance of Each is required
    illTyped { """each.modify(List(1,2,3), _ + 1)""" }

    each[List[Int], Int].modify(_ + 1)(List(1,2,3)) shouldEqual List(2,3,4)
  }

  test("monocle.std._ brings all polymorphic Optic instances in scope for standard Scala classes") {
    import monocle.function._
    import monocle.std._

    // do not compile because Head instance for IList is not in scope
    illTyped { """each[IList[Int], Int]""" }

    each[List[Int], Int].modify(_ + 1)(List(1,2,3))   shouldEqual List(2,3,4)
  }

  test("monocle.interopscalaz._ brings all polymorphic Optic instances in scope for scalaz classes") {
    import monocle.function._
    import monocle.interopscalaz._

    // do not compile because Head instance for List is not in scope
    illTyped { """each[List[Int], Int]""" }
    each[IList[Int], Int].modify(_ + 1)(IList(1,2,3)) shouldEqual IList(2,3,4)
  }


  test("monocle.generic._ brings all polymorphic Optic instances in scope for Shapeless classes") {
    import monocle.function._
    import monocle.generic._

    // do not compile because Each instance for List is not in scope
    illTyped { """each[List[Int], Int].modify(List(1,2,3), _ + 1)""" }

    first[Int :: HNil, Int].modify(_ + 1)(1 :: HNil) shouldEqual (2 :: HNil)
  }

  test("monocle._, Monocle._ makes all Monocle core features available (no generic or scalaz)") {
    import monocle._
    import Monocle._

    each[List[Int], Int].modify(_ + 1)(List(1,2,3))   shouldEqual List(2,3,4)
  }

}


