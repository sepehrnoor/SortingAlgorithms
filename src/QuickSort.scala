package Sort

import java.awt.Color

import scala.collection.mutable.ArrayBuffer

object QuickSort {
  var w: Window = _
  var t: Thread = _
  var b: ArrayBuffer[Int] = _

  def main(args: Array[String]): Unit = {
    val testArray = util.Random.shuffle(0 to 799).toArray
    w = new Window(testArray)
    t = new Thread(w)
    t.run()
    b = ArrayBuffer()
    println(testArray.mkString(" "))
    qsort(testArray)
    println("Done!")
    w.draw()
    println(testArray.mkString(" "))
  }

  def qsort(a: Array[Int], lo: Int, hi: Int): Unit = {
    if (lo < hi) {
      //do nothing if the list a single element
      val p = partition(a, lo, hi) //move elements into two lists, one larger than p and one smaller, ignore p in middle and store it for later use
      qsort(a, lo, p) //sort the left part of the list (smaller than p)
      qsort(a, p + 1, hi) //sort the right part of the list (larger than p)
    }
  }

  def qsort(a: Array[Int]): Unit = {
    qsort(a, 0, a.length - 1)
  }

  def partition(a: Array[Int], lo: Int, hi: Int): Int = {
    val pivot = a(lo)

    var i = lo - 1
    var j = hi + 1

    while (true) {

      do {
        i += 1
      } while (a(i) < pivot)

      do {
        j -= 1
      } while (a(j) > pivot)

      if (i >= j) return j

      swap(a, i, j)
    }
    0
  }

  def swap(a: Array[Int], i1: Int, i2: Int): Unit = {
    val temp = a(i1)
    a(i1) = a(i2)
    a(i2) = temp
    w.draw()
    w.drawBars(Color.red, i1, i2)
    playNote(w,a(i1))
    playNote(w,a(i2))
    Thread.sleep(10)
    SimpleNotePlayer.stop()
  }

  def playNote(w: Window, v: Int): Unit ={
    val n: Int = (12 + (v.toDouble / w.barMax) * 36).toInt
    SimpleNotePlayer.play(n, 50)
  }
}
