package Sort

import java.awt.Color

import scala.collection.mutable.ArrayBuffer

object MergeSort {
  var w: Window = _
  var t: Thread = _
  var testArray: Array[Int] = _

  def main(args: Array[String]): Unit = {
    testArray = util.Random.shuffle(0 to 799).toArray
    w = new Window(testArray)
    t = new Thread(w)
    t.run()
    println(testArray.mkString(" "))
    val sortedArray = mergesort(testArray, 0)
    println("Done!")
    w.draw()
    println(sortedArray.mkString(" "))
  }

  def mergesort(a: Array[Int], pos: Int): Array[Int] = {
    if (a.length <= 1) {
      return a
    }

    val mid = a.size / 2

    val left = mergesort(a.slice(0, mid), pos)
    val right = mergesort(a.slice(mid, a.size), pos + mid)
    val r = merge(left, right)
    updateWindow(r, pos)
    return r
  }

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

    return result.toArray
  }

  def swap(a: Array[Int], i1: Int, i2: Int): Unit = {
    val temp = a(i1)
    a(i1) = a(i2)
    a(i2) = temp
    w.draw()
    w.drawBars(Color.pink, i1, i2)
    Thread.sleep(2)
  }

  def updateWindow(a: Array[Int], start: Int): Unit = {
    for (i <- a.indices) {
      val index = i + start
      val value = a(i)
      replaceAndUpdate(index, value)
    }
  }

  def replaceAndUpdate(index: Int, value: Int): Unit = {
    testArray(index) = value
    w.drawBars(Color.white, index)
    Thread.sleep(10)
  }
}
