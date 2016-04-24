package controller

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Controller Trait - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *          
 *  Notes:
 *      
 *      This trait contains no implementation prevent circular 
 *      dependency between Referee and GameController classes.
 *
 *      Provides signatures to display a match (called from referee)
 *      and attach event handlers to the given view
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import model.Shape

trait Controller {
    def displayMatch (a: Shape, b: Shape)
    def attachHandlers
}