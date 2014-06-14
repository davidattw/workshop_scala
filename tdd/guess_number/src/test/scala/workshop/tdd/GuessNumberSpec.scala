package workshop.tdd

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterEach, Matchers, FunSpec}

@RunWith(classOf[JUnitRunner])
class GuessNumberSpec extends FunSpec with Matchers with BeforeAndAfterEach{
  var game: Game = null
  var controller: StubGameController = null

  override protected def beforeEach(): Unit = {
    controller = new StubGameController
    game = Game(new FakeAnswerGenerator, controller).start()
    controller.onFailed = () => {}
  }

  describe("guess number"){
    it("should return 0A0B when no number is correct") {
      val guessNumber = "5 6 7 8"
      val result = guess(guessNumber)
      result should be("0A0B")
    }

    it("should return 4A0B when all number is correct") {
      val guessNumber = "1 2 3 4"
      val result = guess(guessNumber)
      result should be("4A0B")
    }

    it("should return 1A0B when 1 5 6 7") {
      val guessNumber = "1 5 6 7"
      val result = guess(guessNumber)
      result should be("1A0B")
    }

    it("should return 0A1B when 4 3 2 1") {
      val guessNumber: String = "4 3 2 1"
      val result: String = guess(guessNumber)
      result should be("0A4B")
    }
  }


  private def guess(number: String): String = {
    controller.setGuessNumber(number)
    game.guess()
    controller.guessResult
  }

  describe("guess validate") {
    it("should return Not a valid guess with 1 2") {
      val guessNumber = "1 2"
      val result: String = guess(guessNumber)
      result should be("Not a valid guess")
    }

    it("should return Not a valid guess with 1 2 3 10") {
      val guessNumber = "1 2 3 10"
      val result: String = guess(guessNumber)
      result should be("Not a valid guess")
    }

    it("should return Not a valid guess with 1 1 2 3") {
      val guessNumber = "1 1 2 3"
      val result: String = guess(guessNumber)
      result should be("Not a valid guess")
    }
  }

  describe("random answer generator"){
    it("should generate random answer"){
      val generator: AnswerGenerator = new AnswerGenerator()
      val distinctAnswerSize: Int = (0 to 9).map(i => generator.generate(4)).distinct.size
      distinctAnswerSize should be(10)
    }
  }

  describe("guess history") {
    it("should record every guess result") {
      guess("2 1 6 7")
      guess("1 2 3 4")

      val history: List[String] = controller.getHistory()
      history.size should be(2)
      history(0) should be("2 1 6 7 0A2B")
      history(1) should be("1 2 3 4 4A0B")
    }
  }

  describe("on failed") {
    it("should run on failed when failed") {
      var run = false
      controller.onFailed = () => { run = true}
      guess("1 2 4 5")
      run should be(true)
    }

    it("should exit when failed number reach to 6") {
      controller.onFailed = () => { }
      (0 to 5).foreach(i => guess("1 2 4 5"))
      controller.guessResult should be("You are lose")
    }
  }
}

class FakeAnswerGenerator extends AnswerGenerator{
  override def generate(length: Int): Answer = {
    return Answer("1 2 3 4")
  }
}

class StubGameController extends GameController {
  var guessResult: String = ""

  override def in(): String = {
    guessNumber
  }

  override def print(result: String) {
    guessResult = result
  }

  def setGuessNumber(number: String) {
    guessNumber = number
  }
  def getHistory(): List[String] = {
    history.toList
  }
}
