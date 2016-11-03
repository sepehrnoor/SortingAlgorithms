package Sort

import java.awt.Color

import scala.collection.mutable.ArrayBuffer

object Main {

  def main(args: Array[String]): Unit = {
    testInPlaceSort(800, Algorithms.quickSort, "Quick sort")
    testInPlaceSort(100, Algorithms.bubbleSort, "Bubble sort")
    testInPlaceSort(100, Algorithms.selectionSort, "Selection sort")
    testOOPSort(800, Algorithms.mergeSort, "Merge sort")
  }

  def testInPlaceSort(sampleSize: Int, fun: (Array[Int], Window) => Unit, name: String): Unit = {
    val testArray = util.Random.shuffle(0 to sampleSize - 1).toArray
    val w = new Window(testArray, name + s", sample size $sampleSize")
    val t = new Thread(w, name)
    t.start()
    w.draw()
    println(testArray.mkString(" "))
    fun(testArray, w)
    println("Done!")
    w.draw()
    println(testArray.mkString(" "))

  }

  def testOOPSort(sampleSize: Int, fun: (Array[Int], Array[Int], Window) => Array[Int], name: String): Unit = {
    val testArray = util.Random.shuffle(0 to sampleSize - 1).toArray
    val w = new Window(testArray, name + s", sample size $sampleSize")
    val t = new Thread(w, name)
    t.run()
    w.draw()
    println(testArray.mkString(" "))
    val sorted = fun(testArray, testArray, w)
    println("Done!")
    w.draw()
    println(sorted.mkString(" "))
  }

}

object Algorithms {

  def quickSort(a: Array[Int], lo: Int, hi: Int, w: Window): Unit = {
    if (lo < hi) {
      //do nothing if the list a single element
      val p = partition(a, lo, hi, w) //move elements into two lists, one larger than p and one smaller, ignore p in middle and store it for later use
      quickSort(a, lo, p, w) //sort the left part of the list (smaller than p)
      quickSort(a, p + 1, hi, w) //sort the right part of the list (larger than p)
    }
  }

  def quickSort(a: Array[Int], w: Window): Unit = {
    quickSort(a, 0, a.length - 1, w)
  }

  def partition(a: Array[Int], lo: Int, hi: Int, window: Window): Int = {
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
      updateWindow(2, window, i, j)
    }
    0
  }


  def bubbleSort(a: Array[Int], window: Window): Unit = {
    for (j <- a.length - 1 to 0 by -1) {
      for (i <- 0 until j) {
        if (a(i) > a(i + 1)) {
          swap(a, i, i + 1)
          updateWindow(2, window, i, i + 1)
        }
      }
    }
  }


  def selectionSort(a: Array[Int], window: Window): Unit = {
    for (j <- a.length - 1 to 0 by -1) {
      for (i <- 0 to j) {
        if (a(j) < a(i)) {
          swap(a, j, i)
          updateWindow(2, window, i, j)
        }
      }
    }
  }

  def updateWindow(delay: Long, window: Window, inds: Int*): Unit = {
    window.draw()
    window.drawBars(Color.red, inds: _*)
    inds.foreach(x => playNote(window, window.arr(x)))
    Thread.sleep(delay)
    SimpleNotePlayer.stop()
  }


  def mergeSort(a: Array[Int], pos: Int, origArray: Array[Int], window: Window): Array[Int] = {
    if (a.length <= 1) {
      return a
    }

    val mid = a.length / 2

    val left = mergeSort(a.slice(0, mid), pos, origArray, window)
    val right = mergeSort(a.slice(mid, a.length), pos + mid, origArray, window)
    val r = merge(left, right)
    replaceArray(r, origArray, pos, window)
    return r
  }

  def mergeSort(a: Array[Int], origArray: Array[Int], window: Window): Array[Int] = mergeSort(a, 0, origArray, window)

  def merge(l: Array[Int], r: Array[Int]): Array[Int] = {
    var left = l
    var right = r
    val result = ArrayBuffer[Int]()

    while (!left.isEmpty && !right.isEmpty) {
      if (left.head <= right.head) {
        result += left.head
        left = left.tail
      } else {
        result += right.head
        right = right.tail
      }
    }

    while (!left.isEmpty) {
      result += left.head
      left = left.tail
    }

    while (!right.isEmpty) {
      result += right.head
      right = right.tail
    }

    result.toArray
  }

  def swap(a: Array[Int], i1: Int, i2: Int): Unit = {
    val temp = a(i1)
    a(i1) = a(i2)
    a(i2) = temp
  }

  def replaceArray(newArray: Array[Int], arrayToReplace: Array[Int], start: Int, window: Window): Unit = {
    for (i <- newArray.indices) {
      val index = i + start
      val value = newArray(i)
      replaceAndUpdate(index, value, arrayToReplace, window, 2)
    }
  }

  def replaceAndUpdate(index: Int, value: Int, targetArray: Array[Int], targetWindow: Window, delay: Int): Unit = {
    targetArray(index) = value
    targetWindow.drawBars(Color.white, index)
    playNote(targetWindow, value)
    Thread.sleep(delay)
    SimpleNotePlayer.stop()
  }

  def playNote(w: Window, v: Int): Unit = {
    val n: Int = (12 + (v.toDouble / w.barMax) * 36).toInt
    SimpleNotePlayer.play(n, 50)
  }
}
