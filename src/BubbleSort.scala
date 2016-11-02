package Sort

import java.awt.Color

object BubbleSort {
  var w: Window = _
  var t: Thread = _

  def main(args: Array[String]): Unit = {
    val testArray = util.Random.shuffle(0 to 99).toArray
    w = new Window(testArray)
    t = new Thread(w)
    t.run()
    println(testArray.mkString(" "))
    bubblesort(testArray)
    println("Done!")
    w.draw()
    println(testArray.mkString(" "))
  }

  def bubblesort(a: Array[Int]): Unit = {
    for (j <- a.length - 1 to 0 by -1) {
      for (i <- 0 to j - 1) {
        if (a(i) > (a(i + 1))) {
          swap(a, i, i + 1)
        }
      }
    }
  }

  def swap(a: Array[Int], i1: Int, i2: Int): Unit = {
    val temp = a(i1)
    a(i1) = a(i2)
    a(i2) = temp
    w.draw()
    w.drawBars(Color.red, i1, i2)
    val n1: Int = ((a(i1).toDouble / w.barMax) * 36).toInt
    val n2: Int = ((a(i2).toDouble / w.barMax) * 36).toInt
    SimpleNotePlayer.play(n1,50)
    SimpleNotePlayer.play(n2,50)
    Thread.sleep(2)
    SimpleNotePlayer.stop()
  }
}
