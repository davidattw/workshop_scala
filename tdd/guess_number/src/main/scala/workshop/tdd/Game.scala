package workshop.tdd

object Main extends App{
  override def main(args: Array[String]): Unit = {
    val controller: GameController = new GameController()
    val game = Game(new AnswerGenerator(),controller)
    controller.onFailed = () => game.guess()
    game.start().guess()
  }
}

object Game{
  val numberLength = 4
  val maxTryNumber = 6
  val validator = new InputValidator(Array(new LengthValidator(Game.numberLength),new NumberValidator()))

  def apply(answerGenerator: AnswerGenerator,controller: GameController): Game = {
    new Game(answerGenerator,controller)
  }
}
class Game(answerGenerator: AnswerGenerator, controller: GameController) {
  private var actualAnswer: Answer = null

  def start(): Game = {
    controller.init()
    actualAnswer = answerGenerator.generate(Game.numberLength)
    this
  }

  def guess() = {
    try{
      val guessNumber = controller.in()
      val answer = Answer(guessNumber)
      controller.out(actualAnswer.compare(answer))
    }catch{
      case ex: IllegalArgumentException => controller.out(ex.getMessage)
    }
  }

}

class GameController{
  protected var guessNumber: String = ""
  protected val history = collection.mutable.ArrayBuffer[String]()
  var onFailed: (()=> Unit) = null

  def init(){
    history.clear()
  }
  def in(): String = {
    guessNumber = Console.readLine()
    guessNumber
  }
  def out(answerResult: AnswerResult){
    val result: String = answerResult.out((b,c) => f"$b%sA$c%sB")
    addHistory(result)
    print(result)

    if(answerResult.right()){
      return
    }
    next
  }

  def out(result: String){
    addHistory(result)
    print(result)
    next
  }

  private def outHistory(){
    history.foreach(println(_))
  }

  protected def print(result: String) {
    println(result)
  }

  private def addHistory(result: String){
    history += f"$guessNumber $result"
  }

  private def reachToMaxTryNumber: Boolean = {
    history.size >= Game.maxTryNumber
  }

  private def next {
    if (reachToMaxTryNumber) {
      print("You are lose")
    } else {
      outHistory()
      onFailed()
    }
  }

}