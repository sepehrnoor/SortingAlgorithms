package Sort

import java.awt.{Color, Graphics}
import javax.swing._

class Window(a: Array[Int]) extends JFrame() with Runnable {
  val defaultWidth = 800
  val defaultHeight = 800
  var barSize = 1
  val barMax = a.max
  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  if (a.length > defaultWidth) {
    setSize(a.length, defaultHeight)
    barSize = 1
  }
  barSize = defaultWidth / a.length
  val newWidth = barSize * a.length
  setSize(newWidth, defaultHeight)
  setVisible(true)
  createBufferStrategy(3)
  var strategy = getBufferStrategy
  var running: Boolean = false
  val delay: Long = 15L
  var coloredBuffer: Array[Int] = Array()

  def run(): Unit = {
    running = true
    draw()
  }

  def draw(): Unit = drawBars(Color.white, a: _*)

  def drawBars(color: Color, bars: Int*): Unit = {
    do {
      do {
        //clear the screen
        val graphics: Graphics = strategy.getDrawGraphics
        //draw the bars
        for (i <- bars) {
          val x = i * barSize //x-coordinate
          val h = (a(i).toDouble / barMax * defaultHeight).toInt //height of bar
          val y = defaultHeight - h //y-coordinate
          val w = barSize //width of bar
          graphics.setColor(Color.black)
          graphics.fillRect(x, 0, w, getSize.height)
          graphics.setColor(color)
          graphics.fillRect(x, y, w, h)
        }
        //dispose the graphics
        graphics.dispose()
      } while (strategy.contentsRestored)
      strategy.show()
    } while (strategy.contentsLost)
  }

  def addToColorBuffer(index: Int): Unit = {
    val newBuffer = new Array[Int](coloredBuffer.length + 1)
    for (i <- coloredBuffer.indices){
      newBuffer(i) = coloredBuffer(i)
    }
    newBuffer(coloredBuffer.length) = index
    coloredBuffer = newBuffer
  }

  def updateColors(): Unit = {
    drawBars(Color.white, coloredBuffer: _*)
    coloredBuffer = Array()
  }
}
