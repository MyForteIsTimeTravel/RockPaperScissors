package components

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Signature Class - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *          
 *  Notes:
 *      
 *      Given a piece of text it will be displayed at the bottom
 *      of the screen in white
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

class Signature (screenWidth: Int, screenHeight: Int, text: String) extends GraphicsObject {
    val fontSize = 12

    width = text.length + 2 // sort of cheating...
    x     = screenWidth / 2 - width
    y     = screenHeight - 10

    val color = "#9E9E9E"

    /** 
     *  render
     *  
     *  Description:
     *      Renders the given text at the bottom of the screen
     */
    def render (context: GraphicsContext) {
        context.setFont  (Font.font("Tahoma", fontSize))
        context.setFill  (Color.web(color))
        context.fillText (text, x, y)
    }
}