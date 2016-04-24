package components

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Backdrop Class - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *          
 *  Notes:
 *      
 *      An instantiatable backdrop with a gradient fill that 
 *      gives the illusion of a shadow beneath the header
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.Color
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import java.util.ArrayList

class Backdrop (screenWidth: Int, screenHeight: Int) {
    val x      = 0
    val y      = 0
    val width  = screenWidth
    val height = screenHeight

    val startColor  = "#616161"
    val finishColor = "#9E9E9E"
    val stops       = new ArrayList[Stop]

    stops.add(new Stop (0, Color.web(startColor)))
    stops.add(new Stop (1, Color.web(finishColor)))

    val shadow = new LinearGradient(
        screenWidth/2,
        0,
        screenWidth/2, 
        80, 
        false, 
        CycleMethod.NO_CYCLE, stops
    )

    /** 
     *  render
     *  
     *  Description:
     *      When given a graphics context, this function
     *      draws a backdrop with the above state. using
     *      a gradient to simulate depth.
     */
    def render (context: GraphicsContext) {
        context.setFill  (shadow)
        context.fillRect (x, y, width, height)
    }
}