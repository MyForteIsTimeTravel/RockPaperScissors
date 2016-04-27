package model

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Message class - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 * 
 *  Notes:
 *
 *      A message consists of the ID of the sender, a move and an
 *      MVar result to set by the referee and then be collected
 *      by the player post-game.
 *
 *      Everything in this class is public as it needs to be accessed
 *      by referees, players and potentially the controller
 * 
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
final class Message (id: Int, hand: Shape) {
    val sender = id
    val move   = hand
    val result = new MVar[Int]

    /** 
     *  beats
     *
     *  @param Message
     *
     *  Returns true if the opponent move beats the 
     *  sending players move. This is achieved by 
     *  defering to the Shape class's function of
     *  the same name.
     *      
     */
    def beats (opponent: Message): Boolean = {
        this.move beats opponent.move
    }
}
