package workshop.tdd

object Answer{
  private val validator = new InputValidator(Array(new LengthValidator,new NumberValidator))

  def apply(answerNumber: String): Answer = {
    val numbers: Array[Int] = answerNumber.split(" ").map(_.toInt).distinct
    validator.validate(numbers)
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

class InputValidator(val validators: Array[Validator]) extends Validator{
  def validate(numbers: Array[Int]) {
    validators.foreach(v => v.validate(numbers))
  }
}

trait Validator {
  def validate(numbers: Array[Int])
}
class LengthValidator extends Validator{
  override def validate(numbers: Array[Int]) {
    if (numbers.length != 4) {
      throw new IllegalArgumentException("Not a valid guess")
    }
  }
}

class NumberValidator extends Validator{
  override def validate(numbers: Array[Int]): Unit = {
    if (numbers.exists(i => i < 0 || i > 9)) {
      throw new IllegalArgumentException("Not a valid guess")
    }
  }
}
