package launcher

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  GameLauncher Class & GameLauncher Singleton Object - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *          
 *  Notes:
 *      
 *      Class/Object for launching the JavaFX Application.
 *
 *      Utilises a Companion Object pattern to launch the 
 *      Application from a static context.
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import view.View
import controller.GameController

class GameLauncher extends Application {

    /** 
     *  start
     *  
     *  @param Stage
     *  
     *  This function initialises the parameters for and 
     *  launches the JavaFX Application
     */
    override def start (stage: Stage) = {
        stage.setTitle      ("rock, paper, scissors")
        stage.setResizable  (false)

        val screenWidth  = 900
        val screenHeight = 500
        val view         = new View  (screenWidth, screenHeight)
        val scene        = new Scene (view, screenWidth, screenHeight)
        val controller   = new GameController (stage, scene)

        stage.setScene (scene)
        stage.show

        view.presentWelcomeContent.start
    }
}

/** 
 *  Companion Object Pattern
 *      
 *  Provides a static context from which
 *  to launch the FX Application
 */
object GameLauncher {

    /** 
     *  main
     * 
     *  @param Array[String]
     * 
     *  Calls checkCPU to query the hardware before
     *  launching the FX application
     */
    def main (args: Array[String]) = {
        checkCPU

        Application.launch (
            classOf[GameLauncher], args: _*
        )
    }
 
    /** 
     *  checkCPU
     *
     *  Checks if the current system is capable of
     *  concurrent execution. Prints out if this is
     *  a multicore system and therefore capable of
     *  concurrency.
     */
    private final def checkCPU = {
        print("\u001b[2J\u001b[0;0H")
        println("executing cores: " + Runtime.getRuntime.availableProcessors)
        if (Runtime.getRuntime.availableProcessors > 1)
            println("Concurrency Available\n")
        else
            println("Concurrency unavailable\n")
    }
}
