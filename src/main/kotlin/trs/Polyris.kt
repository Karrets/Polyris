package trs

import processing.core.PApplet
import kaiju.math.Matrix2d

fun main(args: Array<String>)
{
    PApplet.main("trs.Polyris")
}
class Polyris:PApplet()
{
    var lastDrawTime = System.currentTimeMillis() / 1000
    var nextPos = 0f
    var horizontalLocation = 0f
    val boxSize = 50f
    var blockReachedBottom = false
    var xInternal1 = 0f
    var xInternal2 = boxSize
    var yInternal1 = 0f
    var yInternal2 = boxSize
    var disableDrop = false
    val xSize = 9
    val ySize = 14
    val stuckPieces = Matrix2d<Boolean>(xSize, ySize, { x, y -> y > 6 })

    override fun settings()
    {
        size(450, 650) //Should be a multiple of variable boxSize
    }
    override fun setup()
    {
        fill(229f, 27f, 212f)
        background(0f,0f,0f)
    }
    override fun draw() //IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA
    {
        playerInput()
        canBoxMove()
        drawStuckPieces()
        doLoop(false)
        doHacks(true)

    }//IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA, IMPORTANT AREA
    fun drawStuckPieces()
    {

    }
    fun doHacks(doHacks: Boolean)
    {
        if(doHacks == true)
        {
            if(keyPressed == true)
            {
                if(key == 'p')
                {
                    keyPressed = false
                    blockReachedBottom = false
                    nextPos = 0f
                    background(0f,0f,0f)
                    drawBox(horizontalLocation, nextPos)
                }
                if(key == 'o')
                {
                    keyPressed = false
                    disableDrop = !disableDrop
                }
            }
        }
    }
    fun doLoop(doLoop: Boolean)
    {
        if(doLoop == true)
        {
            if(blockReachedBottom == true)
            {
                blockReachedBottom = false
                nextPos = 0f
            }
        }
    }
    fun canBoxMove()
    {
        if(disableDrop == false)
        {
            var tsLong = System.currentTimeMillis() / 1000
            if (tsLong - lastDrawTime > 0.5) {
                background(0f, 0f, 0f)
                drawBox(horizontalLocation, nextPos) //horizontalLocation must be 0 - 9, nextPos must be 0 - 14
                if (nextPos >= height / 50 - 1) {
                    blockReachedBottom = true;
                }
                else
                {
                    nextPos += 1f
                }
                lastDrawTime = System.currentTimeMillis() / 1000
            }
        }
    }
    fun drawBox(x:Float, y:Float)
    {
        xInternal1 = x * boxSize
        xInternal2 = x * boxSize + boxSize
        yInternal1 = y * boxSize
        yInternal2 = y * boxSize + boxSize
        beginShape()
        vertex(xInternal1, yInternal1)
        vertex(xInternal2, yInternal1)
        vertex(xInternal2, yInternal2)
        vertex(xInternal1, yInternal2)
        endShape(CLOSE)
    }
    fun playerInput()
    {
        if(keyPressed)
        {
            if(key == 'a')
            {
                keyPressed = false
                background(0f,0f,0f)
                if(blockReachedBottom == false)
                {
                    horizontalLocation -= 1f
                }
                if(horizontalLocation < 0)
                {
                    horizontalLocation = 0f
                }
                drawBox(horizontalLocation, nextPos)
            }
            if(key == 'd')
            {
                keyPressed = false
                background(0f,0f,0f)
                if(blockReachedBottom == false)
                {
                    horizontalLocation += 1f
                }
                if(horizontalLocation > width / boxSize - 1)
                {
                    horizontalLocation = width / boxSize - 1
                }
                drawBox(horizontalLocation, nextPos)
            }
            if(key == 's')
            {
                keyPressed = false
                background(0f,0f,0f)
                if(blockReachedBottom == false)
                {
                    nextPos = height / boxSize - 1
                    blockReachedBottom = true
                }
                drawBox(horizontalLocation, nextPos)
            }
        }
    }
}