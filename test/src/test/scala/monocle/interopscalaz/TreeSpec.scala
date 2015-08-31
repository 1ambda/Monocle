package monocle.interopscalaz

import cats.std.int._
import cats.std.stream._
import monocle.MonocleSuite
import monocle.law.discipline.LensTests
import monocle.law.discipline.function.{EachTests, ReverseTests}

import scalaz.Tree


class TreeSpec extends MonocleSuite {
  checkAll("rootLabel", LensTests(rootLabel[Int]))
  checkAll("subForest", LensTests(subForest[Int]))
  checkAll("leftMostLabel", LensTests(leftMostLabel[Int]))
  checkAll("rightMostLabel", LensTests(rightMostLabel[Int]))

  checkAll("each Tree", EachTests[Tree[Int], Int])
  checkAll("reverse Tree", ReverseTests[Tree[Int], Tree[Int]])
}
