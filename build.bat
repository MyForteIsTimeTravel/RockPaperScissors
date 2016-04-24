:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::  build.dat
::
::  A Batch script to build the project on windows systems
::  seen as I doubt the bash compatability will be making its
::  to win 7 any time soon.
::
::  Doesn't wipe class files, just replaces them but does 
::  everything else the shell script does.
::
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

REM COMPONENT TRAITS AND CLASSES
call scalac src/components/GraphicsObject.scala     -classpath bin -d bin -sourcepath src
call scalac src/components/Backdrop.scala           -classpath bin -d bin -sourcepath src
call scalac src/components/Header.scala             -classpath bin -d bin -sourcepath src
call scalac src/components/ShowcaseSprite.scala     -classpath bin -d bin -sourcepath src
call scalac src/components/Body.scala               -classpath bin -d bin -sourcepath src
call scalac src/components/Signature.scala          -classpath bin -d bin -sourcepath src
call scalac src/components/Popup.scala              -classpath bin -d bin -sourcepath src
call scalac src/components/GameList.scala           -classpath bin -d bin -sourcepath src
call scalac src/components/GameWindow.scala         -classpath bin -d bin -sourcepath src
call scalac src/controller/Controller.scala         -classpath bin -d bin -sourcepath src

REM Model
call scalac src/model/Shape.scala                   -classpath bin -d bin -sourcepath src
call javac  src/model/MVar.java                     -classpath bin -d bin -sourcepath src
call scalac src/model/Message.scala                 -classpath bin -d bin -sourcepath src
call scalac src/model/Player.scala                  -classpath bin -d bin -sourcepath src
call scalac src/model/Referee.scala                 -classpath bin -d bin -sourcepath src
call scalac src/model/DumbPlayer.scala              -classpath bin -d bin -sourcepath src
call scalac src/model/HumanPlayer.scala             -classpath bin -d bin -sourcepath src

REM View
call scalac src/view/View.scala                     -classpath bin -d bin -sourcepath src

REM Controller
call scalac src/controller/GameController.scala     -classpath bin -d bin -sourcepath src

REM Launcher
call scalac src/launcher/GameLauncher.scala         -classpath bin -d bin -sourcepath src
call scalac src/model/test/ComputerGameTest.scala   -classpath bin -d bin -sourcepath src
call scalac src/model/test/HumanGameTest.scala      -classpath bin -d bin -sourcepath src

cd bin
call scala launcher.GameLauncher
::call scala model.HumanGameTest
::call scala model.ComputerGameTest
cd ../