package workshop.tdd

object Game {
  val numberLength = 4
  val validator = new InputValidator(Array(new LengthValidator(Game.numberLength),new NumberValidator()))

  def apply(answerGenerator: AnswerGenerator): Game = {
    new Game(answerGenerator)
  }
}
class Game(answerGenerator: AnswerGenerator) {
  var actualAnswer: Answer = null
  def start(): Game = {
    actualAnswer = answerGenerator.generate(Game.numberLength)
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
