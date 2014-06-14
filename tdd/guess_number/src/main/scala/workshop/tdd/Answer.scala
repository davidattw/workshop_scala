package workshop.tdd

import scala.util.Random

object Answer{
  def apply(answerNumber: String): Answer = {
    val numbers: Array[Int] = answerNumber.split(" ").map(_.toInt).distinct
    Game.validator.validate(numbers)
    new Answer(numbers)
  }
}
class Answer(private val numbers: Array[Int]) {
  def compare(other: Answer): AnswerResult = {
    val numberCorrectCount = numbers.intersect(other.numbers).size
    val bullsCount = numbers.zip(other.numbers).count(p => p._1 == p._2)
    val cowsCount = numberCorrectCount - bullsCount
    new AnswerResult(bullsCount, cowsCount, numbers.length)
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Answer]

  override def equals(other: Any): Boolean = other match {
    case that: Answer =>
      (that canEqual this) &&
        numbers == that.numbers
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(numbers)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

class AnswerResult(bullsCount: Int, cowsCount: Int, numbersCount: Int){
  def out(action: (Int,Int) => String): String = {
    action(bullsCount,cowsCount)
  }

  def right(): Boolean = {
    bullsCount == numbersCount
  }
}
class AnswerGenerator() {
  def generate(length: Int): Answer = {
    new Answer(next(length))
  }

  private def next(length: Int): Array[Int] = {
    val tryCount: Int = length * 2
    (0 to 9).map(i => Random.nextInt(tryCount)).distinct.take(length).toArray
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
class LengthValidator(length: Int) extends Validator{
  override def validate(numbers: Array[Int]) {
    if (numbers.length != length) {
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
