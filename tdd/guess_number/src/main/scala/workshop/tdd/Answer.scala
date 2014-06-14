package workshop.tdd

object Answer{
  def apply(answerNumber: String): Answer = {
    new Answer(answerNumber.split(" ").map(_.toInt))
  }
}
class Answer(private val numbers: Array[Int]) {
  def compare(other: Answer): String = {
    val numberCorrectCount = numbers.intersect(other.numbers).size
    val bullsCount = numbers.zip(other.numbers).count(p => p._1 == p._2)
    val cowsCount = numberCorrectCount - bullsCount
    f"$bullsCount%sA$cowsCount%sB"
  }
}
