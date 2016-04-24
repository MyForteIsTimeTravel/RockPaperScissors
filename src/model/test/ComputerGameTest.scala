package model.test

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  ComputerGameTest Object - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *          
 *  Notes:
 *      
 *      A command line based test for running DumbPlayer threads against
 *      eachother for a given number of turns.
 *
 *      Each member of the players array is set going
 *      and the main program then waits for each thread
 *      to stop before sending the shutdown signal to the 
 *      referee.
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import java.util.Scanner
import model._

object ComputerGameTest {
    private val referee  = new Referee
    private var nplayers = 1
    private var nturns   = 0

    def main (args: Array[String]) = {
        checkCPU
        getParameters

        val players = Array.tabulate (nplayers) { p =>
            new Thread (new DumbPlayer (p, nturns, referee))
        }

        new Thread (referee).start
        
        players.foreach (x => x.start)
        players.foreach (x => x.join)

        referee.shutdown
    }

    /** 
     *  checkCPU
     *
     *  Description:
     *      Prints the core count of the CPU and
     *      whether or not it supports concurrency
     */
    private final def checkCPU = {
        print("\u001b[2J\u001b[0;0H")
        println("executing cores: " + Runtime.getRuntime.availableProcessors)
        if (Runtime.getRuntime.availableProcessors > 1)
            println("Concurrency Available\n")
        else
            println("Concurrency unavailable\n")
    }

    /** 
     *  getParameters
     *
     *  Description:
     *      Queries the user for running parameters,
     *      enforcing an even player count.
     */
    private final def getParameters = {
        val input = new Scanner(System.in)
        // enforce even player count
        while (nplayers % 2 != 0) {
            println("enter player count (even): ")
            nplayers = input.nextInt
        }

        // turn count can be odd
        println("\nenter turns count: ")
        nturns = input.nextInt
        
        input.close
    }
}