package monocle.thirdparty

import monocle.Prism
import scalaz.{ \/-, -\/, \/ }

object either extends EitherInstances

trait EitherInstances {
  
  def left[A, B, C]: Prism[A \/ B, C \/ B, A, C] =
    Prism[A \/ B, C \/ B, A, C](-\/.apply, _.swap.bimap(\/-.apply, identity))

  def right[A, B, C]: Prism[A \/ B, A \/ C, B, C] =
    Prism[A \/ B, A \/ C, B, C](\/-.apply, _.bimap(-\/.apply, identity))
}
