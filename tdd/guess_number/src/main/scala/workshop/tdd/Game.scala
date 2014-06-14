package workshop.tdd

object Game {
  def apply(actualAnswerNumber: String): Game = {
    new Game(actualAnswerNumber)
  }
}
class Game(actualAnswerNumber: String) {
  var actualAnswer: Answer = null
  def start(): Game = {
    actualAnswer = Answer(actualAnswerNumber)
    this
  }

  def guess(guessNumber: String): String = {
    try{
      val answer: Answer = Answer(guessNumber)
      actualAnswer.compare(answer)
    }catch{
      case ex: IllegalArgumentException => ex.getMessage
    }
  }
}
