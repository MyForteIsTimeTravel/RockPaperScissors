:::::::::::::::::::::::::::::::::::::::::::
::	runGame.bat
::
::	A batch script that moves to
::  the bin, launches the game and
::  returns to the top level directory
::
:::::::::::::::::::::::::::::::::::::::::::

cd bin
call scala launcher.GameLauncher
::call scala model.HumanGameTest
::call scala model.ComputerGameTest
cd ../