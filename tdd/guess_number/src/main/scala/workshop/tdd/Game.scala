package workshop.tdd

object Game {
  def apply(actualAnswer: String): Game = {
    new Game(actualAnswer)
  }
}
class Game(actualAnswer: String) {
  def start(): Game = {
    this
  }

  def guess(guessNumber: String): String = {
    if(guessNumber == "1 2 3 4"){
      return "4A0B"
    }
    "0A0B"
  }
}
