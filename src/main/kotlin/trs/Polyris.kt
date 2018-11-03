package trs

import kaiju.math.Matrix2d
import processing.core.PApplet

fun main(args: Array<String>) {
    PApplet.main("trs.Polyris")
}

class Polyris : PApplet() {
    var lastDrawTime = System.currentTimeMillis() / 1000
    var nextPos = 0
    var horizontalLocation = 4
    val boxSize = 50f
    val xSize = 9
    val ySize = 14
    val stuckPieces = Matrix2d<Boolean>(xSize, ySize, { x, y -> y > 13 })

    override fun settings() {
        size(xSize * boxSize.toInt(), ySize * boxSize.toInt()) //Should be a multiple of variable boxSize
    }

    override fun setup() {
        fill(229f, 27f, 212f)
        background(0f, 0f, 0f)
    }

    override fun draw() //IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA
    {
        playerInput()
        canBoxMove()
        drawStuckPieces()

    }//IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA

    fun addBlocksToArray() {
        if (horizontalLocation.toInt() > 9) {

        } else if (nextPos.toInt() - 1 > 14) {

        } else {
            stuckPieces[horizontalLocation.toInt(), nextPos.toInt() - 1] = true
            nextPos = 0
            horizontalLocation = 4
        }
    }

    fun drawStuckPieces() {
        for (x in 0 until xSize) {
            for (y in 0 until ySize) {
                if (stuckPieces[x, y]) {
                    fill(26f, 228f, 43f)
                    drawBox(x.toFloat(), y.toFloat())
                    fill(229f, 27f, 212f)
                }
            }
        }
    }

    fun canBoxMove() {
        var tsLong = System.currentTimeMillis() / 1000
        if (tsLong - lastDrawTime > 0.5) {
            if (stuckPieces[horizontalLocation.toInt(), nextPos.toInt()]) {
                addBlocksToArray()
            } else {
                background(0f, 0f, 0f)
                drawBox(horizontalLocation.toFloat(), nextPos.toFloat()) //horizontalLocation must be 0 - 9, nextPos must be 0 - 14
                if (stuckPieces[horizontalLocation.toInt(), nextPos.toInt()]) {
                    addBlocksToArray()
                } else {
                    nextPos += 1
                }
                lastDrawTime = System.currentTimeMillis() / 1000
            }
        }
    }

    fun drawBox(x: Float, y: Float) {
        var xInternal1 = x * boxSize
        var xInternal2 = x * boxSize + boxSize
        var yInternal1 = y * boxSize
        var yInternal2 = y * boxSize + boxSize
        beginShape()
        vertex(xInternal1, yInternal1)
        vertex(xInternal2, yInternal1)
        vertex(xInternal2, yInternal2)
        vertex(xInternal1, yInternal2)
        endShape(CLOSE)
    }

    fun playerInput() {
        if (keyPressed) {
            if (key == 'a') {
                if (stuckPieces[horizontalLocation.toInt() - 1, nextPos.toInt() - 1]) {
                    //Blocked
                } else {
                    keyPressed = false
                    background(0f, 0f, 0f)
                    horizontalLocation -= 1
                    if (horizontalLocation < 0) {
                        horizontalLocation = 0
                    }
                    drawBox(horizontalLocation.toFloat(), nextPos.toFloat() - 1)
                }
            }
            if (key == 'd') {
                if (stuckPieces[horizontalLocation.toInt() - 1, nextPos.toInt() - 1]) {
                    //Blocked
                } else {
                    keyPressed = false
                    background(0f, 0f, 0f)
                    if (horizontalLocation > xSize - 2) {
                        horizontalLocation = xSize - 2
                    }
                    horizontalLocation += 1
                    drawBox(horizontalLocation.toFloat(), nextPos.toFloat() - 1)
                }
            }
        }
    }
}