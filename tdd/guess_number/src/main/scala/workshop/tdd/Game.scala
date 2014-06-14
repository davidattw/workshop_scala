package workshop.tdd

object Game {
  def apply(answerGenerator: AnswerGenerator): Game = {
    new Game(answerGenerator)
  }
}
class Game(answerGenerator: AnswerGenerator) {
  var actualAnswer: Answer = null
  def start(): Game = {
    actualAnswer = answerGenerator.generate(4)
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
