package components

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

class Popup (screenWidth: Int, screenHeight: Int, title: String) extends GraphicsObject {
    width  = screenWidth / 3
    height = screenHeight / 4
    x      = screenWidth / 2 - width / 2
    y      = screenHeight / 2 - height / 2

    var backgroundColor = "#414141"

    /** 
     *  render
     *  
     *  Description:
     *      Renders a small pop-up window with some text
     */
    def render (context: GraphicsContext) {
        context.setFill   (Color.web(backgroundColor))
        context.fillRect  (x, y, width, height)
        context.setFont   (Font.font("Tahoma", 16))
        context.setFill   (Color.web("#9E9E9E"))
        context.fillText  (title, x + width / 2 - 24, y + 18)
        context.fillText  ("players", screenWidth / 2 - 128, y + 48)
        context.fillText  ("turns"  , x + width - 62  , y + 48)
        this.renderShadow (context)
    }
}