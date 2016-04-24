package components

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Header Class - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *          
 *  Notes:
 *      
 *      Given a label and screen dimensions this class renders a header with 
 *      the given string as the title
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

class Header (screenWidth: Int, screenHeight: Int, label: String) extends GraphicsObject {
    x      = 0
    y      = 0
    width  = screenWidth
    height = screenHeight / 8

    val backgroundColor = "#FEBD00"
    val labelColor      = "#212121"

    /** 
     *  render
     *  
     *  Description:
     *      Renders a header with the above state
     *      and the given label.
     */
    def render (context: GraphicsContext) {
        context.setFill  (Color.web(backgroundColor))
        context.fillRect (x, y, width, height)

        context.setFill      (Color.web(labelColor))
        context.setLineWidth (2)
        context.setFont      (Font.font("Tahoma", 30))
        context.fillText     (label, width / 24, height - 20)
    }
}