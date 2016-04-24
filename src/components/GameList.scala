package components

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Referee Class - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *          
 *  Notes:
 *      
 *      A pane for the list of previous games. Renders a shadow
 *      using the GraphicsObject shadow function
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color

class GameList (screenWidth: Int, screenHeight: Int) extends GraphicsObject {
    x      = 660
    y      = 80
    width  = 200
    height = 400

    val backgroundColor = "#414141"

    /** 
     *  render
     *  
     *  Description:
     *      This function renderes a pane for the
     *      games list with the above state.
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