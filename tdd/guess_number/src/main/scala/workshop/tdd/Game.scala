package workshop.tdd

object Game {
  val numberLength = 4
  val validator = new InputValidator(Array(new LengthValidator(Game.numberLength),new NumberValidator()))

  def apply(answerGenerator: AnswerGenerator): Game = {
    new Game(answerGenerator)
  }
}
class Game(answerGenerator: AnswerGenerator) {
  private var actualAnswer: Answer = null
  private val history = collection.mutable.ArrayBuffer[String]()
  def start(): Game = {
    actualAnswer = answerGenerator.generate(Game.numberLength)
    this
  }

  def guess(guessNumber: String): String = {
    try{
      val answer: Answer = Answer(guessNumber)
      val result = actualAnswer.compare(answer).out((b,c) => f"$b%sA$c%sB")
      history += f"$guessNumber $result"
      result
    }catch{
      case ex: IllegalArgumentException => ex.getMessage
    }
  }

  def getHistory(): List[String] = {
    history.toList
  }
}
