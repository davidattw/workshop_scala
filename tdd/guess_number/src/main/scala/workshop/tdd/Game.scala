package workshop.tdd

object Game {
  def apply(actualAnswer: String): Game = {
    new Game(actualAnswer)
  }
}
class Game(actualAnswer: String) {
  var answerNumbers: Array[Int] = null
  def start(): Game = {
    answerNumbers = actualAnswer.split(" ").map(_.toInt)
    this
  }

  def guess(guessNumber: String): String = {
    val guessNumbers: Array[Int] = guessNumber.split(" ").map(_.toInt)
    val bullCount: Int = (0 to 3).zip(guessNumbers).intersect((0 to 3).zip(answerNumbers)).length
    f"$bullCount%sA0B"
  }
}
