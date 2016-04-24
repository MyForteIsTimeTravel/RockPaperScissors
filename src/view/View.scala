package view

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  View - rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 *      - Animations need re-doing
 *          
 *  Notes:
 *      
 *  
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
import javafx.animation.AnimationTimer
import java.util.ArrayList
import javafx.geometry.Pos
import javafx.geometry.Insets
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.VBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.Pane
import javafx.scene.layout.HBox
import javafx.scene.shape.Rectangle
import javafx.event.EventHandler
import javafx.scene.input.KeyEvent
import javafx.scene.control.ListView
import javafx.collections._
import components._

class View (screenWidth: Int, screenHeight: Int) extends StackPane {
    /** 
     *  Load assets
     */
    val rockImg     = new Image("assets/rock.png")
    val paperImg    = new Image("assets/paper.png")
    val scissorsImg = new Image("assets/scissors.png")

    /** 
     *  The user interface is constructed of 2 layers stacked
     *  ontop of one another. The canvasLayer draws the visual
     *  nicities and deals with animation effects whereas the 
     *  interface layer creates buttons and other interactable
     *  components
     *
     *  Additional scenes and views are all contained within these
     *  two nodes and are made "active" and "inactive" through 
     *  transitions.
     *
     */
    val canvasLayer    = new Canvas (screenWidth, screenHeight)
    val interfaceLayer = new Pane
    this.getChildren.addAll (canvasLayer, interfaceLayer)

    /** 
     *  INITIALIZE INTERFACE LAYER
     *
     *      The interface layer consists of all controls for
     *      all pseudo-views that will be added and removed
     *      from the pane as required
     *
     */
    interfaceLayer.getStylesheets.add   ("assets/interface.css")

    val backButton          = new Button    ("back")
    val startButtons        = new HBox
    val computerStart       = new Button    ("computer vs. computer")
    val humanStart          = new Button    ("computer vs. human")
    val exit                = new Button    ("exit")
    val signature           = new Button    ("Ryan Needham\n2016")

    val inputFields         = new HBox
    val playersInput        = new ComboBox[Int]
    val turnsInput          = new ComboBox[Int]
    val startGame           = new Button    ("go")

    val gameView            = new HBox
    var playerOneSprite     = new ImageView (rockImg)
    val serperator          = new Rectangle (0, 0, 30, 10)
    var playerTwoSprite     = new ImageView (rockImg)

    val controlsContainer   = new VBox

    val gameControls        = new HBox
    val pauseButton         = new Button ("pause execution")
    val stopButton          = new Button ("stop execution")
    val quitButton          = new Button ("quit")

    val humanControls       = new HBox
    val humanRockButton     = new Button ("rock")
    val humanPaperButton    = new Button ("paper")
    val humanScissorsButton = new Button ("scissors")

    val previousGamesView   = new ListView[HBox]; // list view of HBoxes containing 2 images?
    val previousGamesList   = FXCollections.observableArrayList (new HBox) // only renders bottom item?

    startButtons.setAlignment           (Pos.CENTER)
    startButtons.setSpacing             (screenWidth / 5)
    startButtons.getChildren.addAll     (computerStart, humanStart)
    startButtons.setLayoutX             (screenWidth / 5 - startButtons.getWidth - 12)
    startButtons.setLayoutY             (screenHeight / 2 + 60)

    exit.setLayoutX                     (screenWidth / 2 - 32)
    exit.setLayoutY                     (screenHeight / 2 + 128)

    gameView.setAlignment               (Pos.CENTER)
    gameView.setSpacing                 (60)
    gameView.getChildren.addAll         (playerOneSprite, serperator, playerTwoSprite)
    gameView.setLayoutX                 (160)
    gameView.setLayoutY                 ((screenHeight / 5) * 2)

    gameControls.setAlignment           (Pos.CENTER)
    gameControls.setSpacing             (32)
    gameControls.getChildren.addAll     (stopButton, quitButton)

    humanControls.setAlignment          (Pos.CENTER)
    humanControls.setSpacing            (86)
    humanControls.getChildren.addAll    (humanRockButton, humanPaperButton, humanScissorsButton)

    controlsContainer.setAlignment      (Pos.CENTER)
    controlsContainer.setSpacing        (20)
    controlsContainer.getChildren.addAll(gameControls)
    controlsContainer.setLayoutX        (110)
    controlsContainer.setLayoutY        (350)

    inputFields.setAlignment            (Pos.CENTER)
    inputFields.setSpacing              (110)
    inputFields.getChildren.addAll      (playersInput, turnsInput)
    inputFields.setLayoutX              (screenWidth / 2 - 128)
    inputFields.setLayoutY              (screenHeight / 2)
    startGame.setLayoutX                (screenWidth / 2 - 32)
    startGame.setLayoutY                (screenHeight / 2 + 80)

    previousGamesView.setItems          (previousGamesList);
    previousGamesView.setMaxWidth       (192)
    previousGamesView.setMaxHeight      (392)
    previousGamesView.setLayoutX        (648)
    previousGamesView.setLayoutY        (84)

    // Initialise parameter list
    var i = 0
    for (i <- 0 to 100) {
        if (i % 2 == 0)
            playersInput.getItems.add(i)
    }

    for (i <- 0 to 100) {
        turnsInput.getItems.add(i)
    }

    playersInput.setVisibleRowCount(5)
    turnsInput.setVisibleRowCount  (5)

    /** 
     *  INITIALIZE CANVAS/DRAW LAYER
     *
     *      The canvas layer consists of "shapes" that 
     *      make up the visual effects of the UI. All 
     *      components can be found in the components 
     *      package and extend the trait "GraphicsObject"
     *
     */
    val context  = canvasLayer.getGraphicsContext2D
    val backdrop = new Backdrop (screenWidth, screenHeight)
    val header   = new Header   (screenWidth, screenHeight, "Rock, Paper, Scissors")

    val welcomeContent: Array[GraphicsObject] = Array (
        new ShowcaseSprite (1, screenWidth, screenHeight, rockImg),
        new ShowcaseSprite (2, screenWidth, screenHeight, paperImg),
        new ShowcaseSprite (3, screenWidth, screenHeight, scissorsImg),
        new Body           (screenWidth, screenHeight),
        new Signature      (screenWidth, screenHeight, "2016")
    )

    val optionsContent: Array[GraphicsObject] = Array (
        new Popup (screenWidth, screenHeight, "options")
    )

    val gameContent: Array[GraphicsObject] = Array (
        new GameWindow (screenWidth, screenHeight),
        new GameList   (screenWidth, screenHeight)
    )

    /** 
     *  CANVAS LAYER ANIMATIONS
     *
     *      This section of code contains animation sequences
     *      for the canvas layer as well as removing/adding
     *      UI elements based on the current pseudo-view
     *
     */
    def hideWelcomeContent = {
        welcomeContent(0).setX (0.0 - welcomeContent(0).getWidth)
        welcomeContent(1).setY (0.0 - welcomeContent(1).getHeight)
        welcomeContent(2).setX (screenWidth)
        welcomeContent(3).setY (screenHeight + 20)
    }

    def hideOptionsContent = {
        optionsContent(0).setY (screenHeight)
    }

    def hideGameContent = {
        gameContent(0).setX (0 - gameContent(0).getWidth)
        gameContent(1).setX (screenWidth + gameContent(0).getWidth) // set to + other width on purpose
    }

    var velocity:          Double = 0.0
    var velocityIncrement: Double = 0.4
    var fadeIncrement:     Double = 0.05
    
    // elements all removed from viewable area
    hideWelcomeContent
    hideOptionsContent
    hideGameContent

    // Animations to bring them into view
    val presentWelcomeContent = new AnimationTimer {
        override def start = {
            super.start

            hideWelcomeContent
            interfaceLayer.setOpacity(0)
            interfaceLayer.getChildren.addAll(startButtons, exit)
        }

        override def handle(currentNanoTime: Long) = {
            // MOVE THINGS IN
            if (welcomeContent(0).getX + (welcomeContent(0).getWidth / 2) < (screenWidth / 5) * 1 &&
                welcomeContent(1).getY < 128 &&
                welcomeContent(2).getX + (welcomeContent(2).getWidth / 2) > (screenWidth / 3) * 4 ||
                welcomeContent(3).getY > (screenHeight / 5) * 3) {

                welcomeContent(0).setX(welcomeContent(0).getX + velocity)
                welcomeContent(1).setY(welcomeContent(1).getY + velocity)
                welcomeContent(2).setX(welcomeContent(2).getX - velocity)
                welcomeContent(3).setY(welcomeContent(3).getY - velocity)

            } else {
                this.stop
            }

            // FADE CONTROLS
            if (interfaceLayer.getOpacity < 1)
                interfaceLayer.setOpacity(interfaceLayer.getOpacity + fadeIncrement)

            renderWith(welcomeContent)
            velocity += velocityIncrement
        }

        override def stop = {
            super.stop
            velocity = 0.0
        }
    }

    val dismissWelcomeContentAi = new AnimationTimer { 
        override def handle (currentNanoTime: Long) = {
            // MOVE THINGS OUT
            if (welcomeContent(0).getX - (welcomeContent(0).getWidth) < 0 &&
                welcomeContent(1).getY > 128 &&
                welcomeContent(2).getX < screenWidth ||
                welcomeContent(3).getY < screenHeight) {

                welcomeContent(0).setX(welcomeContent(0).getX - velocity)
                welcomeContent(1).setY(welcomeContent(1).getY - velocity)
                welcomeContent(2).setX(welcomeContent(2).getX + velocity)
                welcomeContent(3).setY(welcomeContent(3).getY + velocity)
            } else {
                this.stop
            }

            // FADE CONTROLS
            if (interfaceLayer.getOpacity > 0)
                interfaceLayer.setOpacity(interfaceLayer.getOpacity - fadeIncrement)

            renderWith(welcomeContent)
            velocity += velocityIncrement
        }

        override def stop = {
            super.stop
            hideWelcomeContent
            interfaceLayer.getChildren.clear

            presentOptionsContent.start

            velocity = 0.0
        }
    }

    val presentOptionsContent = new AnimationTimer {
        override def start = {
            super.start

            hideOptionsContent
            interfaceLayer.setOpacity(0)
            interfaceLayer.getChildren.addAll(inputFields, startGame)
        }

        override def handle (currentNanoTime: Long) = {
            // MOVE THINGS IN

            if (optionsContent(0).getY > screenHeight / 2 - optionsContent(0).getHeight / 2)
                optionsContent(0).setY (optionsContent(0).getY - velocity)
            else 
                this.stop

            // FADE CONTROLS
            if (interfaceLayer.getOpacity < 1)
                interfaceLayer.setOpacity(interfaceLayer.getOpacity + fadeIncrement)

            renderWith(optionsContent)
            velocity += velocityIncrement
        }

        override def stop = {
            super.stop
            velocity = 0.0
        }
    }

    val dismissOptionsContent = new AnimationTimer {
        override def handle (currentNanoTime: Long) = {
            // MOVE THINGS OUT
            if (optionsContent(0).getY < screenHeight)
                optionsContent(0).setY (optionsContent(0).getY + velocity)
            else 
                this.stop

            // FADE CONTROLS
            if (interfaceLayer.getOpacity > 0)
                interfaceLayer.setOpacity(interfaceLayer.getOpacity - fadeIncrement)

            renderWith(optionsContent)
            velocity += velocityIncrement
        }

        override def stop = {
            super.stop
            hideOptionsContent
            interfaceLayer.getChildren.clear
            presentAiGameContent.start
            velocity = 0.0
        }
    }

    val presentAiGameContent = new AnimationTimer {
        override def start = {
            super.start
            gameControls.getChildren.add(pauseButton)
            hideGameContent
            interfaceLayer.setOpacity(0)
            interfaceLayer.getChildren.addAll (gameView, controlsContainer, previousGamesView)

        }

        override def handle (currentNanoTime: Long) = {
            if (gameContent(0).getX < 60)
                gameContent(0).setX(gameContent(0).getX + velocity)


            if (gameContent(1).getX > screenWidth - (gameContent(1).getWidth + 41))
                gameContent(1).setX(gameContent(1).getX - velocity)
            else 
                this.stop

            if (interfaceLayer.getOpacity < 1)
                interfaceLayer.setOpacity(interfaceLayer.getOpacity + fadeIncrement)

            renderWith(gameContent)
            velocity += velocityIncrement
        }

        override def stop = {
            super.stop
            velocity = 0.0
        }
    }

    val dismissAiGameContent = new AnimationTimer {
        override def handle (currentNanoTime: Long) = {
            if (gameContent(0).getX > 0 - gameContent(0).getWidth)
                gameContent(0).setX(gameContent(0).getX - velocity)


            if (gameContent(1).getX < screenWidth + (gameContent(1).getWidth))
                gameContent(1).setX(gameContent(1).getX + velocity)
            else 
                this.stop

            if (interfaceLayer.getOpacity > 0)
                interfaceLayer.setOpacity(interfaceLayer.getOpacity - fadeIncrement)

            renderWith(gameContent)
            velocity += velocityIncrement
        }

        override def stop = {
            super.stop
            gameControls.getChildren.remove(pauseButton)
            hideGameContent
            interfaceLayer.getChildren.clear
            presentWelcomeContent.start
            velocity = 0.0
        }
    }

    val dismissWelcomeContentHuman = new AnimationTimer { 
        override def handle (currentNanoTime: Long) = {
            // MOVE THINGS OUT
            if (welcomeContent(0).getX - (welcomeContent(0).getWidth) < 0 &&
                welcomeContent(1).getY > 128 &&
                welcomeContent(2).getX < screenWidth ||
                welcomeContent(3).getY < screenHeight) {

                welcomeContent(0).setX(welcomeContent(0).getX - velocity)
                welcomeContent(1).setY(welcomeContent(1).getY - velocity)
                welcomeContent(2).setX(welcomeContent(2).getX + velocity)
                welcomeContent(3).setY(welcomeContent(3).getY + velocity)
            } else {
                this.stop
            }

            // FADE CONTROLS
            if (interfaceLayer.getOpacity > 0)
                interfaceLayer.setOpacity(interfaceLayer.getOpacity - fadeIncrement)

            renderWith(welcomeContent)
            velocity += velocityIncrement
        }

        override def stop = {
            super.stop
            hideWelcomeContent
            interfaceLayer.getChildren.clear

            presentHumanGameContent.start

            velocity = 0.0
        }
    }

    val presentHumanGameContent = new AnimationTimer {
        override def start = {
            super.start

            hideGameContent

            controlsContainer.getChildren.add(humanControls)
            interfaceLayer.setOpacity(0)
            interfaceLayer.getChildren.addAll (gameView, controlsContainer, previousGamesView)

        }

        override def handle (currentNanoTime: Long) = {
            if (gameContent(0).getX < 60)
                gameContent(0).setX(gameContent(0).getX + velocity)


            if (gameContent(1).getX > screenWidth - (gameContent(1).getWidth + 41))
                gameContent(1).setX(gameContent(1).getX - velocity)
            else 
                this.stop

            if (interfaceLayer.getOpacity < 1)
                interfaceLayer.setOpacity(interfaceLayer.getOpacity + fadeIncrement)

            renderWith(gameContent)
            velocity += velocityIncrement
        }

        override def stop = {
            super.stop
            velocity = 0.0
        }
    }

    val dismissHumanGameContent = new AnimationTimer {
        override def handle (currentNanoTime: Long) = {
            if (gameContent(0).getX > 0 - gameContent(0).getWidth)
                gameContent(0).setX(gameContent(0).getX - velocity)


            if (gameContent(1).getX < screenWidth + (gameContent(1).getWidth))
                gameContent(1).setX(gameContent(1).getX + velocity)
            else 
                this.stop

            if (interfaceLayer.getOpacity > 0)
                interfaceLayer.setOpacity(interfaceLayer.getOpacity - fadeIncrement)

            renderWith(gameContent)
            velocity += velocityIncrement
        }

        override def stop = {
            super.stop
            hideGameContent
            controlsContainer.getChildren.remove(humanControls)
            interfaceLayer.getChildren.clear
            presentWelcomeContent.start
            velocity = 0.0
        }
    }

    val exitAnimation = new AnimationTimer {
        override def handle (currentNanoTime: Long) = {
            // MOVE THINGS OUT
            if (welcomeContent(0).getX - (welcomeContent(0).getWidth) < 0 &&
                welcomeContent(1).getY > 128 &&
                welcomeContent(2).getX < screenWidth ||
                welcomeContent(3).getY < screenHeight) {

                welcomeContent(0).setX(welcomeContent(0).getX - velocity)
                welcomeContent(1).setY(welcomeContent(1).getY - velocity)
                welcomeContent(2).setX(welcomeContent(2).getX + velocity)
                welcomeContent(3).setY(welcomeContent(3).getY + velocity)
            } else {
                this.stop
            }

            // FADE CONTROLS
            if (interfaceLayer.getOpacity > 0)
                interfaceLayer.setOpacity(interfaceLayer.getOpacity - fadeIncrement)

            renderWith(welcomeContent)
            velocity += velocityIncrement
        }
    }
  
    /** 
     *  renderWith
     *  
     *  Description:
     *      given an array of objects, this function will 
     *      render its given objects between the backdrop 
     *      and header. (backdrop -> content -> header)
     */
    def renderWith (objects: Array[GraphicsObject]) {
        backdrop.render (context)
        objects.foreach (x => x.render(context))
        header.render   (context)
    }
}