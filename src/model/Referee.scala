package model

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Referee Class - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *          
 *  Notes:
 *      
 *      A server program to collect, run and output the results of games
 *      between Player Threads. Utilises an ArrayBlockingQueue to enforce
 *      thread safety.
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import java.util.concurrent.ArrayBlockingQueue
import controller.Controller

final class Referee extends Runnable {
    private val queue     = new ArrayBlockingQueue[Message](2, true)
    private var controller: Controller = null // to be initialised in the custom constructor
    private var paused    = false
    private var running   = true
    private var gameCount = 0

    /** 
     *  Custom Constructor
     *  
     *  @param Controller
     *  
     *  As the default constructor sets the controller
     *  field to null, this constructor accepts a reference
     *  to a controller that can be used to display matches.
     *      
     */
    def this (contr: Controller) = {
        this ()                 // call the default constructor
        this.controller = contr // assign the controller reference
    }

    /** 
     *  addToQueue
     *
     *  takes a reference to a message object and
     *  places it in the queue, waiting for space
     *  to become available if the queue is full.
     */
    def addToQueue (m: Message) = queue put m

    /** 
     *  isPaused & isRunning
     *
     *  these functions are used to check the status
     *  of the referee. They simply return the current
     *  state of the operational variables.
     */
    def isPaused:  Boolean = paused
    def isRunning: Boolean = running

    /** 
     *  pause & unpause
     *
     *  sets the paused field to the opposite of
     *  its current state. If the referee is paused
     *  it will be unpaused and if it is unpaused it
     *  will be paused.
     */
    def pause   = paused = true
    def unpause = paused = false

    /** 
     *  shutdown
     *
     *  sets the referees "running" field to false, 
     *  causing the main loop to break and the referee
     *  to cease execution
     */
    def shutdown = running = false 
  
    /** 
     *  runMatch
     *
     *  safely accesses the queue and removes two messages.
     *  The match is then outputted to the terminal and, if
     *  it is defined, the controller before being sent to the
     *  chooseWinner function to decypher the outcome.
     */
    private def runMatch = {
        var a = queue.take
        var b = queue.take

        print("Player " + a.sender + " (" + a.move.name + ") \t")
        print("vs ")
        print("Player " + b.sender + " (" + b.move.name + ")\t")
        print("=> ")

        if (controller != null)
            controller.displayMatch (a.move, b.move)

        chooseWinner (a, b)
        gameCount += 1
    }

    /** 
     *  chooseWinner
     *
     *  @param Message 
     *  @param Message
     * 
     *  takes two messages that represent a game, uses the
     *  "beats" function from the Message class (which defers
     *  to the Shape class) to decypher the outcome of the match
     *  before informing the participants of the result and 
     *  outputting the results to the console.
     */
    private def chooseWinner (a: Message, b: Message) = {
        if ( a beats b ) {
            println(" Player " + a.sender + " wins!")
            a.result putMVar  1
            b.result putMVar -1 
        } 

        else if ( b beats a ) {
            println(" Player " + b.sender + " wins!")   
            a.result putMVar -1
            b.result putMVar  1
        } 

        else {
            println(" Draw")
            a.result putMVar 0
            b.result putMVar 0
        }
    }
    
    /** 
     *  resetState
     *  
     *  Resets the operational variables for the Referee
     *  to ensure the referee Thread can be safely started.
     */
    def resetState = {
        running   = true
        gameCount = 0
        unpause
    }

    /** 
     *  run
     *
     *  Prints a message to the console informing the
     *  user the referee has started before calling 
     *  reset state to ensure it is safe to start.
     *  The function then iterates until it is shut down
     *  and on each iteration, attempts to run a game (if 
     *  it has not been paused) before sleeping for 1500ms 
     *  to simulate a countdown and prevent an overly aggressive
     *  execution that leads to unfinished player threads. 
     *  
     *  An additional loop is used during shutdown to clear
     *  any remaining players from the session and let them
     *  shut themselves down gracefully.
     */
    def run = {
        resetState
        println("Referee Started...")

        while (running) {
            if (!paused && !queue.isEmpty) 
                runMatch
            Thread.sleep(1500) // reducing this sleep by too much causes over-aggression
        }

        runShutdownOperations
    }

    /** 
     *  runShutdownOperations
     *  
     *  Here the referee kills off player
     *  threads that may be remaining (due
     *  to a premature exit from the GUI) by 
     *  feeding them an error that exhausts
     *  their turn count.
     */
    private def runShutdownOperations = {
        // not 100% effective.
        while (!queue.isEmpty) {
            queue.poll.result putMVar -3
        }
        
        println("\nReferee Shutting Down...")
        println("Game Count : " + gameCount)    
    }
}
