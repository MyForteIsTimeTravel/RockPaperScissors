package components


/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  GraphicsObject Trait - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *          
 *  Notes:
 *      
 *      A trait for graphics objects. Provides an x, y, width, height
 *      set of fields and getters and setters for said fields.
 *
 *      There is also a shadow rendering function that draws a fading 
 *      gradient rectangle underneath the object. Not the best but it 
 *      works.
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.Color
import java.util.ArrayList

trait GraphicsObject {
    protected var x:      Double = _
    protected var y:      Double = _
    protected var width:  Double = _
    protected var height: Double = _

    private val stops = new ArrayList[Stop]
    stops.add(new Stop (0, Color.web("#424242")))
    stops.add(new Stop (1, Color.web("#9E9E9E")))
    val shadow = new LinearGradient (
        getWidth / 2, 
        getY + getHeight,
        getWidth / 2,
        getY + getHeight + 8, 
        false, 
        CycleMethod.NO_CYCLE, 
        stops
    )

    /** 
     *  get Methods
     *
     *  Description:
     *      methods that return the state of the GraphicsObject
     *      for use in animation calculations.
     */
    def getX:      Double = x
    def getY:      Double = y
    def getWidth:  Double = width
    def getHeight: Double = height

    /** 
     *  set Methods
     *
     *  Description:
     *      methods that set the state of the GraphicsObject
     *      for use in animation and positioning calculations.
     */
    def setX      (newX:      Double) = { x      = newX }
    def setY      (newY:      Double) = { y      = newY } 
    def setWidth  (newWidth:  Double) = { width  = newWidth }
    def setHeight (newHeight: Double) = { height = newHeight }

    /** 
     *  render
     *
     *  To be overriden by implementing classes
     */
    def render (context: GraphicsContext)

    /** 
     *  renderShadow
     *
     *  Given a graphics context, this function will
     *  render a (slightly shoddy) shadow underneath 
     *  the object.
     */
    def renderShadow (context: GraphicsContext) = {
        context.setFill  (shadow)
        context.fillRect (getX, getY + getHeight, getWidth, getY + getHeight + 16)
    }

}