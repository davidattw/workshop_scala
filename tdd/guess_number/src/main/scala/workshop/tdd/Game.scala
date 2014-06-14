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
  protected var guessNumber: String = ""
  protected val history = collection.mutable.ArrayBuffer[String]()

  def in(): String = {
    guessNumber = System.console().readLine()
    guessNumber
  }
  def out(result: String){
    history += f"$guessNumber $result"
    println(history.length)
    print(result)
  }

  def outHistory(){
    history.foreach(println(_))
  }
  protected def print(result: String) {
    println(result)
  }
}