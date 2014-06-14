package workshop.tdd

object Answer{
  def apply(answerNumber: String): Answer = {
    val numbers: Array[Int] = answerNumber.split(" ").map(_.toInt)
    if(numbers.length != 4) {
      throw new IllegalArgumentException("Not a valid guess")
    }
    new Answer(numbers)
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
