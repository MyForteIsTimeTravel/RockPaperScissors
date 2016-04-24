package model

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *  Shape Classes trait and classes- rps
 *  
 *  Author:
 *                  Ryan Needham
 * 
 *  Issues:
 * 
 *  Notes:
 *
 *      Each shape class has a name and a function, beats, that returns
 *      true if the "opponent"/passed shape is beaten by the calling shape 
 *      and false otherwise
 *  
 *      None is used as an initial value for the players hand and for any 
 *      occasion where the player may be without a hand (due to error or 
 *      waiting for user input)
 *
 *      Rock beats Scissors
 *      Scissors beats Paper
 *      Paper beats Rock
 * 
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
sealed trait Shape {
    def name: String
    def beats (opponent: Shape): Boolean
}

final class Rock extends Shape { 
    def name = "rock"
    def beats (opponent: Shape) = {
        opponent.name == "scissors" ||
        opponent.name == "none"
    }
}

final class Paper extends Shape {
    def name = "paper"
    def beats (opponent: Shape) = {
        opponent.name == "rock" ||
        opponent.name == "none"
    }
}

final class Scissors extends Shape {
    def name = "scissors"
    def beats (opponent: Shape) = {
        opponent.name == "paper" ||
        opponent.name == "none"
    }
}

final class None extends Shape {
    def name = "none"
    def beats (opponent: Shape) = false 
}

/** 
 *  Here is what is happening in a 
 *  more descriptive syntax
 *
 *      def beats(b: Shape) = {
 *          if (b.name == "* the shape this beats *") {
 *              return true
 *          } else {
 *              return false
 *          }
 *      }
 */