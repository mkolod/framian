package pellucid.pframe
package reduce

import scala.annotation.tailrec

import spire.algebra.Field
import spire.syntax.field._

private[reduce] final class Mean[A: Field] extends Reducer[A, Option[A]] {
  def reduce(column: Column[A], indices: Array[Int], start: Int, end: Int): Option[A] = {
    @tailrec def loop(i: Int, sum: A, count: Int): Option[A] = if (i < end) {
      val row = indices(i)
      if (column.exists(row)) {
        loop(i + 1, sum + column.value(row), count + 1)
      } else {
        loop(i + 1, sum, count)
      }
    } else if (count > 0) {
      Some(sum / count)
    } else {
      None
    }

    loop(start, Field[A].zero, 0)
  }
}
