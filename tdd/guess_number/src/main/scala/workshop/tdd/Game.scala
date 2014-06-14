package workshop.tdd

object Main extends App{
  override def main(args: Array[String]): Unit = {
    Game(new AnswerGenerator(),new GameController())
      .start()
      .guess()
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
    if(history.size >= Game.maxTryNumber){
      out("You are lose")
    }else{
      onFailed()
    }
  }

  def out(result: String){
    addHistory(result)
    print(result)
    onFailed()
  }

  def outHistory(){
    history.foreach(println(_))
  }

  protected def print(result: String) {
    println(result)
  }

  private def addHistory(result: String){
    history += f"$guessNumber $result"
  }
}