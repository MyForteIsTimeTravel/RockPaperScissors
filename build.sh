# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
#   build.sh
#
#   A shell script to easily build the entire project.
#
#   This script wipes the class files from the bin folder
#   before compiling every source code file in the project
#   and launching either the full GUI application or one 
#   of the CLI based tests.
#
#   There is also a more basic but still functioning batch
#   version incase this is being built on windows system
#
#   [to adjust whether or not to run the full GUI or a 
#    test program, alter the commented lines at the bottom]
#
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
#!/bin/bash

# Clear class files
cd bin

rm components/*.class
rm model/*.class
rm model/test/*.class
rm view/*.class
rm controller/*.class
rm launcher/*.class

cd ../

# COMPONENT CLASSES AND TRAITS
scalac src/components/GraphicsObject.scala   -classpath bin -d bin -sourcepath src
scalac src/components/Backdrop.scala         -classpath bin -d bin -sourcepath src
scalac src/components/Header.scala           -classpath bin -d bin -sourcepath src
scalac src/components/ShowcaseSprite.scala   -classpath bin -d bin -sourcepath src
scalac src/components/Body.scala             -classpath bin -d bin -sourcepath src
scalac src/components/Signature.scala        -classpath bin -d bin -sourcepath src
scalac src/components/Popup.scala            -classpath bin -d bin -sourcepath src
scalac src/components/GameList.scala         -classpath bin -d bin -sourcepath src
scalac src/components/GameWindow.scala       -classpath bin -d bin -sourcepath src
scalac src/controller/Controller.scala       -classpath bin -d bin -sourcepath src

# Model
scalac src/model/Shape.scala                 -classpath bin -d bin -sourcepath src
javac  src/model/MVar.java                   -classpath bin -d bin -sourcepath src
scalac src/model/Message.scala               -classpath bin -d bin -sourcepath src
scalac src/model/Player.scala                -classpath bin -d bin -sourcepath src
scalac src/model/Referee.scala               -classpath bin -d bin -sourcepath src
scalac src/model/DumbPlayer.scala            -classpath bin -d bin -sourcepath src
scalac src/model/HumanPlayer.scala           -classpath bin -d bin -sourcepath src

# View
scalac src/view/View.scala                   -classpath bin -d bin -sourcepath src

# Controller
scalac src/controller/GameController.scala   -classpath bin -d bin -sourcepath src

# Launcher
scalac src/launcher/GameLauncher.scala       -classpath bin -d bin -sourcepath src
scalac src/model/test/ComputerGameTest.scala -classpath bin -d bin -sourcepath src
scalac src/model/test/HumanGameTest.scala    -classpath bin -d bin -sourcepath src

cd bin
scala launcher.GameLauncher
#scala model.test.ComputerGameTest
#scala model.test.HumanGameTest
cd ../