package model

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  DumbPlayer class - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:     
 *
 *  Notes:
 *      
 *      An extention of the Player trait that selects its move randomly.
 *      For more details see "Player.scala"
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import java.util.Random

final class DumbPlayer (id: Int, turns: Int, ref: Referee) extends Player {
    protected val playerID  = id
    protected val referee   = ref
    protected var turnsLeft = turns
    protected val generator = new Random

    /** 
     *  makeMove
     *
     *  Generates a random float between 1 and 0 before
     *  selecting a hand based on where it sits between 
     *  1 and 0, splitting the range into 3 potential
     *  outcomes.
     */
    protected def makeMove  = {
        var choice = generator.nextFloat

        if      (choice > 0.0 && choice < 0.3) hand = rock
        else if (choice > 0.3 && choice < 0.6) hand = paper
        else if (choice > 0.6 && choice < 1.0) hand = scissors
    } 
}
