package workshop.tdd

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, FunSpec}

@RunWith(classOf[JUnitRunner])
class GuessNumberSpec extends FunSpec with Matchers{
  describe("guess number"){
    it("should return 0A0B when no number is correct") {
      val game: Game = Game("1 2 3 4").start()
      val result: String = game.guess("5 6 7 8")
      result should be("0A0B")
    }

    it("should return 4A0B when all number is correct") {
      val game: Game = Game("1 2 3 4").start()
      val result: String = game.guess("1 2 3 4")
      result should be("4A0B")
    }

  }
}
