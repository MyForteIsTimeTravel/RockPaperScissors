package components

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  ShowcaseSprite Class - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *          
 *  Notes:
 *      
 *      A class for showcase sprites to be displayed on the
 *      welcome screen.
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import javafx.scene.canvas.GraphicsContext
import javafx.scene.image.Image

class ShowcaseSprite (sequence: Int, screenWidth: Int, screenHeight: Int, texture: Image) extends GraphicsObject {
    width  = texture.getWidth
    height = texture.getHeight
    x      = screenWidth / 4 * sequence - 48
    y      = 128

    val skin = texture

    /** 
     *  render
     *  
     *  Description:
     *      Renders the image at the given coordinates
     */
    def render (context: GraphicsContext) {
        context.drawImage (skin, x, y)
    }
}