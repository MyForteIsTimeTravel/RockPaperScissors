package model

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Player Trait - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 * 
 *  Notes:
 *
 *      This trait holds all the base functionality for a "player" and is
 *      extended by specific implementations (i.e. DumbPlayer, HumanPlayer)
 *      that enherit this functionality and add their own "makeMove" function
 *      
 *      The trait extends runnable and provides a "run" method for all players.
 *      Also encapsulated within this trait is functionality for packaging messages, 
 *      calculating the number of games played, preparing an "outcome" statement
 *      and retreiving the results of messages when passed a reference to them
 * 
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
trait Player extends Runnable {
    protected val playerID:  Int
    protected val referee:   Referee
    protected var turnsLeft: Int

    protected val rock        = new Rock
    protected val paper       = new Paper
    protected val scissors    = new Scissors
    protected val none        = new None
    protected var hand: Shape = none

    private var winCount  = 0
    private var lossCount = 0
    private var drawCount = 0

    /**
     *  makeMove
     *
     *  Description:
     *      An abstract method to be implemented by subclasses.
     *      Used to choose a move and assign it to the "hand"
     *      field.
     *
     */
    protected def makeMove

    /** 
     *  makeMessage
     *
     *  Description:
     *      A function that packages the players ID and hand in
     *      a Message object and returns a reference to it.
     */
    private final def makeMessage: Message = new Message (playerID, hand)

    /** 
     *  gamesPlayed
     *  
     *  Description:
     *      returns the number of games played by the player
     *      in this current session. This is done by adding 
     *      the total of wins, losses and draws and returning
     *      the result.
     */
    private final def gamesPlayed: Int = winCount + lossCount + drawCount
    
    /** 
     *  getResultOf
     *
     *  Parameters:
     *      turn    Message     a reference to a Message object representing the current turn
     *
     *  Description:
     *      Extracts the result MVar from the Message object and
     *      updates the players "records" based on the outcome
     *      of the game. The error code "-3" is used to kill off
     *      player threads in the event the referee is shut down
     *      while player threads are still iterating.
     */
    private final def getResultOf (turn: Message) = {
        var result = turn.result.takeMVar

        if      (result ==  1) winCount  += 1
        else if (result ==  0) drawCount += 1
        else if (result == -1) lossCount += 1
        else if (result == -3) turnsLeft  = 0
    }

    /** 
     *  outcome
     *
     *  Description:
     *      returns, as a string, the outcome of this session 
     *      (ID, wins, losses, draws and total number of games)
     */
    private final def outcome: String = {
        "\nPlayer " + playerID    + " Finished" +
        " wins: "   + winCount    +
        " losses: " + lossCount   +
        " draws: "  + drawCount   +
        " total: "  + gamesPlayed + 
        "\n"
    }

    /** 
     *  run
     *
     *  Description:
     *      Prints a message to the console with the players ID
     *      to inform the user the player has started. The function
     *      then iterates for a given number of turns, selecting a 
     *      shape, packaging it in a message, sending it to the 
     *      referee and waiting for a result. Once its turns are 
     *      exhausted the player outputs the results of the session
     *      and ceases exectution.
     */
    final def run = {
        println("Player " + playerID + " started")

        while (turnsLeft > 0) {
            makeMove

            var turn = makeMessage
            referee addToQueue turn
            getResultOf (turn)

            turnsLeft -= 1
        }
        
        println (outcome)
    }
}