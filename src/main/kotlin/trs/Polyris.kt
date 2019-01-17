package trs

import kaiju.math.Matrix2d
import processing.core.PApplet
import processing.core.PConstants
import kotlin.math.roundToInt

fun main(args: Array<String>) {
    PApplet.main("trs.Polyris")
}

class GameState {
    var lastDrawTime = System.currentTimeMillis()
    var nextPos = 0
    val boxSize = 50f
    val xSize = 5 //Should be odd
    val ySize = 7 //Can be anything, try to keep it lower than your vertical resolution divided by 50
    var horizontalLocation = xSize / 2
    val stuckPieces = Matrix2d(xSize, ySize, { x, y -> y > 13 })        //Play Field
    val Polyminos = Matrix2d(4, 5, { x, y -> y > 13 })     //Blocks
}

class Polyris : PApplet() {

    var gameState = GameState()

    override fun settings() {
        size(gameState.xSize * gameState.boxSize.toInt(), gameState.ySize * gameState.boxSize.toInt()) //Should be a multiple of variable boxSize
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
        clearLines()
        newPolyminos()

    }//IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA

    fun clearLines() {
        for (y in 0 until gameState.ySize) {
            var foundGap = false
            for (x in 0 until gameState.xSize) {
                if (!gameState.stuckPieces[x, y]) {
                    foundGap = true
                }
            }
            if (!foundGap) {
                clearRow(y)
            }
        }
    }

    fun clearRow(row: Int) {

        // reversed here switches 0 to row into row to zero
        for (y in (0..row).reversed()) {
            println(y)
            if (y == 0) {
                // reset if we are at the top
                for (x in 0 until gameState.xSize)
                    gameState.stuckPieces[x, y] = false

            } else {
                for (x in 0 until gameState.xSize)
                    gameState.stuckPieces[x, y] = gameState.stuckPieces[x, y - 1]

            }
        }

    }

    fun addBlocksToArray() {
        if (gameState.stuckPieces.contains(gameState.horizontalLocation, gameState.nextPos - 1)) {
            gameState.stuckPieces[gameState.horizontalLocation, gameState.nextPos - 1] = true       //Adds block(s) to the array
            gameState.nextPos = 0                                                                   //Moves blocks to top of screen
            gameState.horizontalLocation = gameState.xSize / 2                                      //Moves blocks to top of screen
        } else {
            gameState = GameState()
        }

    }

    fun drawStuckPieces() {
        for (x in 0 until gameState.xSize) {
            for (y in 0 until gameState.ySize) {
                if (gameState.stuckPieces[x, y]) {
                    fill(26f, 228f, 43f)
                    drawBox(x.toFloat(), y.toFloat())
                    fill(229f, 27f, 212f)
                }
            }
        }
    }

    fun canBoxMove() {
        var tsLong = System.currentTimeMillis()
        if (tsLong - gameState.lastDrawTime > 500) {
            if (gameState.stuckPieces.outside(gameState.horizontalLocation, gameState.nextPos) || gameState.stuckPieces[gameState.horizontalLocation, gameState.nextPos]) {
                addBlocksToArray()
            } else {
                background(0f, 0f, 0f)
                drawBox(gameState.horizontalLocation.toFloat(), gameState.nextPos.toFloat()) //horizontalLocation must be 0 - 9, nextPos must be 0 - 14
                if (gameState.stuckPieces[gameState.horizontalLocation, gameState.nextPos]) {
                    addBlocksToArray()
                } else {
                    gameState.nextPos += 1
                }
                gameState.lastDrawTime = System.currentTimeMillis()
            }
        }
    }

    fun drawBox(x: Float, y: Float) {
        var xInternal1 = x * gameState.boxSize
        var xInternal2 = x * gameState.boxSize + gameState.boxSize
        var yInternal1 = y * gameState.boxSize
        var yInternal2 = y * gameState.boxSize + gameState.boxSize
        beginShape()
        vertex(xInternal1, yInternal1)
        vertex(xInternal2, yInternal1)
        vertex(xInternal2, yInternal2)
        vertex(xInternal1, yInternal2)
        endShape(CLOSE)
    }


    fun playerInput() {
        if (keyPressed) {
            if (key == 'a' || keyCode == PConstants.LEFT) {
                if (gameState.stuckPieces.outside(gameState.horizontalLocation - 1, gameState.nextPos) || gameState.stuckPieces[gameState.horizontalLocation - 1, gameState.nextPos]) {
                    //Blocked
                } else {
                    keyPressed = false
                    background(0f, 0f, 0f)
                    gameState.horizontalLocation -= 1
                    if (gameState.horizontalLocation < 0) {
                        gameState.horizontalLocation = 0
                    }
                    drawBox(gameState.horizontalLocation.toFloat(), gameState.nextPos.toFloat() - 1)
                }
            }
            if (key == 'd' || keyCode == PConstants.RIGHT) {
                if (gameState.stuckPieces.outside(gameState.horizontalLocation + 1, gameState.nextPos) || gameState.stuckPieces[gameState.horizontalLocation + 1, gameState.nextPos]) {
                    //Blocked
                } else {
                    keyPressed = false
                    background(0f, 0f, 0f)
                    if (gameState.horizontalLocation > gameState.xSize - 2) {
                        gameState.horizontalLocation = gameState.xSize - 2
                    }
                    gameState.horizontalLocation += 1
                    drawBox(gameState.horizontalLocation.toFloat(), gameState.nextPos.toFloat() - 1)
                }
            }
            if (key == 's' || keyCode == PConstants.DOWN) {
                keyPressed = false
                gameState.lastDrawTime = 0
            }
        }
    }

    fun newPolyminos() {
        clearMatrix2d()
        //set Polyminos to a new Polymino
        var RNG = Math.random() * 12
        if (RNG.roundToInt() == 0) {
            gameState.Polyminos[0, 0] = true
            gameState.Polyminos[1, 0] = true
            gameState.Polyminos[2, 0] = true
            gameState.Polyminos[0, 1] = true
            gameState.Polyminos[1, 1] = true
        }//DONE
        if (RNG.roundToInt() == 1) {
            gameState.Polyminos[0, 0] = true
            gameState.Polyminos[1, 0] = true
            gameState.Polyminos[2, 0] = true
            gameState.Polyminos[0, 1] = true
            gameState.Polyminos[0, 2] = true
        }//DONE
        if (RNG.roundToInt() == 2) {
            gameState.Polyminos[0, 0] = true
            gameState.Polyminos[0, 1] = true
            gameState.Polyminos[0, 2] = true
            gameState.Polyminos[1, 1] = true
            gameState.Polyminos[2, 1] = true
        }//DONE
        if (RNG.roundToInt() == 3) {
            gameState.Polyminos[2, 0] = true
            gameState.Polyminos[0, 1] = true
            gameState.Polyminos[1, 1] = true
            gameState.Polyminos[2, 1] = true
            gameState.Polyminos[0, 2] = true
        }//DONE
        if (RNG.roundToInt() == 4) {
            gameState.Polyminos[1, 0] = true
            gameState.Polyminos[2, 0] = true
            gameState.Polyminos[0, 1] = true
            gameState.Polyminos[1, 1] = true
            gameState.Polyminos[0, 2] = true
        }//DONE
        if (RNG.roundToInt() == 5) {
            gameState.Polyminos[0, 0] = true
            gameState.Polyminos[1, 0] = true
            gameState.Polyminos[2, 0] = true
            gameState.Polyminos[3, 0] = true
            gameState.Polyminos[4, 0] = true
        }//DONE
        if (RNG.roundToInt() == 6) {
            gameState.Polyminos[0, 0] = true
            gameState.Polyminos[2, 0] = true
            gameState.Polyminos[0, 1] = true
            gameState.Polyminos[1, 1] = true
            gameState.Polyminos[2, 1] = true
        }//DONE
        if (RNG.roundToInt() == 7) {
            gameState.Polyminos[1, 0] = true
            gameState.Polyminos[0, 1] = true
            gameState.Polyminos[1, 1] = true
            gameState.Polyminos[2, 1] = true
            gameState.Polyminos[1, 2] = true
        }//DONE
        if (RNG.roundToInt() == 8) {
            gameState.Polyminos[0, 0] = true
            gameState.Polyminos[1, 0] = true
            gameState.Polyminos[2, 0] = true
            gameState.Polyminos[3, 0] = true
            gameState.Polyminos[0, 1] = true
        }//DONE
        if (RNG.roundToInt() == 9) {
            gameState.Polyminos[2, 0] = true
            gameState.Polyminos[0, 1] = true
            gameState.Polyminos[1, 1] = true
            gameState.Polyminos[2, 1] = true
            gameState.Polyminos[3, 1] = true
        }
        if (RNG.roundToInt() == 10) {
            gameState.Polyminos[2, 0] = true
            gameState.Polyminos[3, 0] = true
            gameState.Polyminos[0, 1] = true
            gameState.Polyminos[1, 1] = true
            gameState.Polyminos[2, 1] = true
        }
        if (RNG.roundToInt() == 11) {
            gameState.Polyminos[2, 0] = true
            gameState.Polyminos[0, 1] = true
            gameState.Polyminos[1, 1] = true
            gameState.Polyminos[2, 1] = true
            gameState.Polyminos[1, 2] = true
        }
    }
    fun clearMatrix2d() {
        for (x in 0 until gameState.Polyminos.xSize) {
            for (y in 0 until gameState.Polyminos.ySize) {
                gameState.Polyminos[x, y] = false
            }
        }
    }
}