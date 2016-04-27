package model

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  HumanPlayer class - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *      User may shut down the program while this thread is 
 *      waiting on its MVar to be set
 *          
 *  Notes:
 *
 *      An extention of the Player trait that selects its move based on
 *      a signal sent from the User Interface (CLI or GUI)
 *
 *      The choice field is an MVar so the game will synchronise on (and 
 *      not proceed without) the humans input. Was originally a regular
 *      integer but this meant the game would often make its move before
 *      the player had a chance to send the signal.
 *
 *      For more details see "Player.scala"
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
final class HumanPlayer (id: Int, turns: Int, ref: Referee) extends Player {
    protected val playerID  = id
    protected val referee   = ref
    protected var turnsLeft = turns
    protected val choice    = new MVar[Int]

    /** 
     *  setChoice
     *
     *  @param Int
     * 
     *  setChoice will be called from the gui on
     *  user prompt and makeMove will use this variable 
     *  as the move. This prevents the program
     *  blocking for i/o on makeMove calls and allows
     *  the game to proceed regardless
     */
    def setChoice (c: Int) = choice.putMVar(c)

    /** 
     *  isChoiceSet - EXPERIMENTAL
     *  
     *  @return Boolean
     * 
     *  Used to attempt to deal with the GUI exitting
     *  the game while the player is making a move, causing
     *  the thread to hang. Queries the "getIsSet" method 
     *  I added to the MVar class
     */
    def isChoiceSet: Boolean = choice.getIsSet
    
    /** 
     *  makeMove
     *
     *  Sets the players hand to the choice made by
     *  the user.
     */
    protected def makeMove = { 
        var c = choice.takeMVar

        if      (c == 1) hand = rock
        else if (c == 2) hand = paper
        else if (c == 3) hand = scissors
    }
}
