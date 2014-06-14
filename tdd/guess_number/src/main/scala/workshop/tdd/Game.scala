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
    "0A0B"
  }
}
