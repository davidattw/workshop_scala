package workshop.tdd

object Answer{
  def apply(answerNumber: String): Answer = {
    new Answer(answerNumber.split(" ").map(_.toInt))
  }
}
class Answer(private val numbers: Array[Int]) {
  def compare(other: Answer): String = {
    val bullCount = numberWithIndex(numbers)
      .intersect(numberWithIndex(other.numbers)).length
    f"$bullCount%sA0B"
  }

  private def numberWithIndex(numbers: Array[Int]): IndexedSeq[(Int, Int)] = {
    (0 to 3).zip(numbers)
  }
}
