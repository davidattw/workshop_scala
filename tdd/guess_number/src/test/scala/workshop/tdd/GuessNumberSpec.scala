package workshop.tdd

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterEach, Matchers, FunSpec}

@RunWith(classOf[JUnitRunner])
class GuessNumberSpec extends FunSpec with Matchers with BeforeAndAfterEach{
  var game: Game = null

  override protected def beforeEach(): Unit = {
    game = Game("1 2 3 4").start()
  }

  describe("guess number"){
    it("should return 0A0B when no number is correct") {
      val guessNumber = "5 6 7 8"
      val result = game.guess(guessNumber)
      result should be("0A0B")
    }

    it("should return 4A0B when all number is correct") {
      val guessNumber = "1 2 3 4"
      val result = game.guess(guessNumber)
      result should be("4A0B")
    }

    it("should return 1A0B when 1 5 6 7") {
      val guessNumber = "1 5 6 7"
      val result = game.guess(guessNumber)
      result should be("1A0B")
    }

    it("should return 0A1B when 4 3 2 1") {
      val guess: String = "4 3 2 1"
      val result: String = game.guess(guess)
      result should be("0A4B")
    }
  }
}
