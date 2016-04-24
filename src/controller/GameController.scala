package controller

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Game Controller - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *      - kill off human player on quit
 *      - Animations leaving things in wrong place on second run
 *          
 *  Notes:
 *      
 *      A Controller class for Rock Paper Scissors with the ability
 *      to create objects for and run "sessions" between multiple AI
 *      threads or a human user and an AI as well as display matches
 *      from the referee on the view.
 *
 *      Contains all event handling for the view class via the attatchHandlers
 *      function.
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import javafx.animation.AnimationTimer
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.event.ActionEvent
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.scene.text.Text
import view.View
import model._

class GameController (stage: Stage, scene: Scene) extends Controller {
    val referee     = new Referee (this)
    val humanPlayer = new HumanPlayer(1, 10, referee)
    val view        = scene.getRoot.asInstanceOf[View]

    /** 
     *  Attatch handlers is called during construction
     */
    attachHandlers

    /** 
     *  makePlayers
     *  
     *  Description
     *      given a number of players and turns, this function will
     *      return an array of player threads with unique IDs and the
     *      given number of turns
     */
    def makePlayers (nplayers: Int, nturns: Int): Array[Thread] = {
        Array.tabulate (nplayers) { p =>
            new Thread(new DumbPlayer(p, nturns, referee))
        }
    }

    /** 
     *  displayMatch
     *
     *  Description
     *      when given two shapes, this function will display
     *      the match in the view's gameWindow before adding 
     *      a representation of the game to the previous games
     *      list. Adding to the list is passed as a runnable to 
     *      the FX Thread for execution as operations on javaFX
     *      observable lists need to be executed on the FX thread
     */
    def displayMatch (a: Shape, b: Shape) = {
        view.playerOneSprite.setImage(new Image ("assets/" + a.name + ".png"))
        view.playerTwoSprite.setImage(new Image ("assets/" + b.name + ".png"))

        val game = new HBox
        game.setSpacing(28)

        val playerOne = new ImageView(new Image ("assets/" + a.name + ".png"))
        playerOne.setFitWidth (40)
        playerOne.setFitHeight(30)

        val versus = new Text ("vs.")

        val playerTwo = new ImageView(new Image ("assets/" + b.name + ".png"))
        playerTwo.setFitWidth (40)
        playerTwo.setFitHeight(30)

        game.getChildren.addAll(playerOne, versus, playerTwo)

        // send to fx thread   
        Platform.runLater (new Runnable {
            def run = view.previousGamesList.add(game)
        })
    }

    /** 
     *  attatchHandlers
     *
     *  Description:
     *      This function attatches handlers to the elements
     *      in the views UI Layer using annonymous classes.
     *
     *      Games are given their own thread to allow waiting
     *      for all threads to join without interfeering with
     *      graphics rendering
     */
    def attachHandlers = {

        /** 
         *  Welcome Screen Handlers
         */
        view.computerStart.setOnAction(new EventHandler[ActionEvent] {
            override def handle (e: ActionEvent) = {
                view.dismissWelcomeContentAi.start
            }
        })

        view.humanStart.setOnAction(new EventHandler[ActionEvent] {
            override def handle (e: ActionEvent) = {
                view.dismissWelcomeContentHuman.start
                view.playerOneSprite.setImage(new Image("assets/none.png"))
                view.playerTwoSprite.setImage(new Image("assets/none.png"))

                /** 
                 * The game is set running from a concurrent thread
                 * that can join the threads without interfeering with
                 * the javaFX Application thread
                 */
                new Thread (new Runnable {
                    val refereeThr = new Thread(referee)
                    val players    = Array(
                        new Thread (humanPlayer),
                        new Thread (new DumbPlayer (2, 10, referee))
                    )

                    def run = {
                        refereeThr.start

                        players.foreach (x => x.start)
                        players.foreach (x => x.join)

                        view.playerOneSprite.setImage(new Image("assets/gameOver.png"))
                        view.playerTwoSprite.setImage(new Image("assets/gameOver.png"))

                        referee.shutdown
                        refereeThr.join

                        println("Game Complete")
                    }
                }).start
            }
        })

        view.exit.setOnAction(new EventHandler[ActionEvent] {
            override def handle (e: ActionEvent) = {
                view.exitAnimation.start
                stage.close

                /**
                 *  kill off any threads that may potentially have
                 *  slipped through error signal feeds now
                 *  that the user is done and they have
                 *  no use. Alternative to this would be better.
                 */
                if (referee.isRunning) 
                    referee.shutdown

                if (!humanPlayer.isChoiceSet)
                    humanPlayer.setChoice(0)

                System.exit (0)
            }
        })

        /** 
         *  Options Screen Handlers
         */
        view.startGame.setOnAction(new EventHandler[ActionEvent] {
            override def handle (e: ActionEvent) = {
                val nplayers = view.playersInput.getValue
                val nturns   = view.turnsInput.getValue

                println("Launching Game")
                println(nplayers + " players")
                println(nturns   + " turns")

                view.dismissOptionsContent.start

                /** 
                 * The game is set running from a concurrent thread
                 * that can join the threads without interfeering with
                 * the javaFX Application thread
                 */
                new Thread (new Runnable {
                    val refereeThr = new Thread (referee)
                    val players    = makePlayers(nplayers, nturns)

                    def run = {
                        refereeThr.start

                        players.foreach (x => x.start)
                        players.foreach (x => x.join)

                        view.playerOneSprite.setImage(new Image("assets/gameOver.png"))
                        view.playerTwoSprite.setImage(new Image("assets/gameOver.png"))

                        referee.shutdown
                        refereeThr.join

                        println("Game Complete")
                    }
                }).start
            }
        })

        /** 
         * Game Screen Handlers
         */
        view.pauseButton.setOnAction(new EventHandler[ActionEvent] {

            override def handle (e: ActionEvent) = {
                if (referee.isPaused) {
                    referee.unpause
                    view.pauseButton.setText ("pause execution")
                    println("referee resumed...")
                }
                else {
                    referee.pause
                    view.pauseButton.setText ("resume execution")
                    println("referee paused...")
                }
            }
        })

        view.stopButton.setOnAction(new EventHandler[ActionEvent] {
            override def handle (e: ActionEvent) = {
                referee.shutdown
                println("referee shutdow signal sent")  
            }
        })

        view.quitButton.setOnAction(new EventHandler[ActionEvent] {
            override def handle (e: ActionEvent) = {
                referee.shutdown

                view.dismissHumanGameContent.start
                view.dismissAiGameContent.start

                // send to fx thread
                Platform.runLater (new Runnable {
                    def run = view.previousGamesList.clear
                })
            }
        })

        /** 
         *  Human Controls Handlers
         */
        view.humanRockButton.setOnAction(new EventHandler[ActionEvent] {
            override def handle (e: ActionEvent) = {
                humanPlayer.setChoice(1)
            }
        })

        view.humanPaperButton.setOnAction(new EventHandler[ActionEvent] {
            override def handle (e: ActionEvent) = {
                humanPlayer.setChoice(2)
            }
        })

        view.humanScissorsButton.setOnAction(new EventHandler[ActionEvent] {
            override def handle (e: ActionEvent) = {
                humanPlayer.setChoice(3)
            }
        })
    }
}