package components

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Body Class - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *          
 *  Notes:
 *      
 *      A Body class that figures out its dimensions given a screen width
 *      and a screen height
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class Body (screenWidth: Int, screenHeight: Int) extends GraphicsObject {
    x      = 0
    y      = (screenHeight / 3) * 2
    width  = screenWidth
    height = (screenHeight / 3) * 2

    val color = "#414141"

    /** 
     *  render
     *  
     *  Description:
     *      This function renderes a content body pane
     *      with the above state.
     */
    def render (context: GraphicsContext) {
        context.setFill  (Color.web(color))
        context.fillRect (x, y, width, height)
    }
}