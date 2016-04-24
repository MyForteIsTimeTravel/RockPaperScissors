# # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
#   runGame.sh
#
#   Moves the current working directory to
#   bin and runs the program, returning to
#   the top level folder after the run.
#
# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # 
cd bin
scala launcher.GameLauncher
#scala model.test.ComputerGameTest
#scala model.test.HumanGameTest
cd ../