package monocle.std

import monocle.Prism
import scalaz.{ -\/, \/- }

object either extends EitherInstances

trait EitherInstances {
  
  def left[A, B, C]: Prism[Either[A, B], Either[C, B], A, C] =
    Prism[Either[A, B], Either[C, B], A, C](Left.apply, {
      case Left(a)  => \/-(a)
      case Right(b) => -\/(Right(b))
    })

  def right[A, B, C]: Prism[Either[A, B], Either[A, C], B, C] =
    Prism[Either[A, B], Either[A, C], B, C](Right.apply, {
      case Left(a)  => -\/(Left(a))
      case Right(b) => \/-(b)
    })

}
