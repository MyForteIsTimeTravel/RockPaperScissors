package components

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  GameWindow Class - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *          
 *  Notes:
 *      
 *      A space to overlay the game view on. Renders a shadow
 *      using the standard GraphicsObject shadow function
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class GameWindow (screenWidth: Int, screenHeight: Int) extends GraphicsObject {
    x      = 40
    y      = 80
    width  = (screenWidth / 12) * 7
    height = (screenHeight / 5) * 4

    val backgroundColor = "#414141"

    /** 
     *  render
     *  
     *  Description:
     *      This function renderes a pane for the
     *      games view with the above state.
     *
     *      Calls GraphicsObject's renderShadow to
     *      simulate a shadow
     */
    def render (context: GraphicsContext) {
        context.setFill  (Color.web(backgroundColor))
        context.fillRect (x, y, width, height)
        renderShadow     (context)
    }
}