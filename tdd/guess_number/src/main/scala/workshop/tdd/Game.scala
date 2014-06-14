package workshop.tdd

object Game {
  val numberLength = 4
  val validator = new InputValidator(Array(new LengthValidator(Game.numberLength),new NumberValidator()))

  def apply(answerGenerator: AnswerGenerator,controller: GameController): Game = {
    new Game(answerGenerator,controller)
  }
}
class Game(answerGenerator: AnswerGenerator, controller: GameController) {
  private var actualAnswer: Answer = null
  private val history = collection.mutable.ArrayBuffer[String]()
  def start(): Game = {
    actualAnswer = answerGenerator.generate(Game.numberLength)
    this
  }

  def guess() = {
    try{
      val guessNumber: String = controller.in()
      val answer: Answer = Answer(guessNumber)
      val result = actualAnswer.compare(answer).out((b,c) => f"$b%sA$c%sB")
      history += f"$guessNumber $result"
      controller.out(result)
    }catch{
      case ex: IllegalArgumentException => controller.out(ex.getMessage)
    }
  }

  def getHistory(): List[String] = {
    history.toList
  }
}

class GameController{
  def in(): String = {
    System.console().readLine()
  }
  def out(output: String){
    println(output)
  }
}