package model.test

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  HumanGameTest Object - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *          
 *  Notes:
 *      A command line based test for running humans against computer players
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import java.util.Scanner 
import model._

object HumanGameTest {
    private val NTURNS  = 100
    private val referee = new Referee
    private val human   = new HumanPlayer (1, NTURNS, referee)
    private val player1 = new Thread      (human)
    private val player2 = new Thread      (new DumbPlayer  (2, NTURNS, referee))
    private val input   = new Scanner     (System.in)

    def main (args: Array[String]) = {
        checkCPU
        splashScreen

        new Thread (referee).start

        player1.start
        player2.start

        while (player1.isAlive) {
            getUserInput
        }

        input.close
        player1.join
        player2.join
        referee.shutdown
    }

    /** 
     *  checkCPU
     *
     *  Prints the core count of the CPU and
     *  whether or not it supports concurrency
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
     *  splashScreen
     *
     *  A welcome screen with "press start to begin"
     */
    private final def splashScreen = {
        println("ROCK, PAPER, SCISSORS: Human edition")
        println("Press enter to start...")
        System.in.read
    }

    /** 
     *  getUserInput
     *
     *  prompts the user to select a turn.
     */
    private final def getUserInput = {
            println("\n")
            println(" press 1 and enter for rock")
            println(" press 2 and enter for paper")
            println(" press 3 end enter for scissors")
            print  (" previous game > ")

            human.setChoice(input.nextInt)
    }
}
