/*
App Name: MathGame
Author: woodsj1206 (https://github.com/woodsj1206)
Description: A simple two-player game to help children practice arithmetic.
Course: CIS 436
Date Created: 2/16/24
Last Modified: 3/17/24
*/
package com.example.mathgame

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mathgame.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    //Set the initial values for the game.
    private val player1 = Player("Player 1", R.color.player_1)
    private val player2 = Player("Player 2", R.color.player_2)

    private var leftOperand = 0
    private var rightOperand = 0
    private var operator = ' '

    //Jackpot starts at 5 points.
    private var jackpot = 5
    private var tryingJackpot = false

    private var questionValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Display the initial values in the text views.
        binding.tvPlayer1.text = player1.getPlayerName()
        binding.tvPlayer2.text = player2.getPlayerName()

        //Display the score and jackpot.
        updateScore()

        //Decide which player goes first.
        var currentPlayer : Player = if(Random.nextInt(0,2) == 0) player1 else player2

        //Display the current player's turn.
        binding.tvStatus.text = getString(R.string.players_turn, currentPlayer.getPlayerName())
        binding.tvStatus.setTextColor(ContextCompat.getColor(this, currentPlayer.getPlayerColor()))

        binding.llQuestion.visibility = View.INVISIBLE
        binding.tvResult.visibility = View.INVISIBLE
        binding.btnPlayAgain.visibility = Button.INVISIBLE

        binding.btnRollDie.setOnClickListener {
            binding.tvResult.visibility = View.INVISIBLE

            //Roll the die and display the corresponding die face.
            val rollDie = if(tryingJackpot) Random.nextInt(1,4) else Random.nextInt(1,7)
            val dieFace = getDieFace(rollDie)
            binding.ivDie.setImageResource(dieFace)

            //Define actions corresponding to each die face.
            when (rollDie) {
                //Addition.
                1-> {
                    //Addition is limited to numbers between 0 and 99.
                    leftOperand = Random.nextInt(0, 100)
                    rightOperand = Random.nextInt(0, 100)
                    operator = '+'

                    questionValue = 1

                    updateDisplay(getString(R.string.addition), currentPlayer)
                }
                //Subtraction.
                2-> {
                    //Subtraction is limited to numbers between 0 and 99.
                    leftOperand = Random.nextInt(0, 100)
                    rightOperand = Random.nextInt(0, 100)
                    operator = '-'

                    questionValue = 2

                    updateDisplay(getString(R.string.subtraction), currentPlayer)
                }
                //Multiplication.
                3-> {
                    //Multiplication is limited to numbers between 0 and 20.
                    leftOperand = Random.nextInt(0, 21)
                    rightOperand = Random.nextInt(0, 21)
                    operator = 'x'

                    questionValue = 3

                    updateDisplay(getString(R.string.multiplication), currentPlayer)
                }
                //Roll again with chance to get double points.
                4-> {
                    currentPlayer.setMultiplier(2)

                    updateResult(getString(R.string.player_rolls_again_with_double_points, currentPlayer.getPlayerName()), currentPlayer)
                }
                //Lose a turn.
                5-> {
                    //Display text for losing a turn.
                    updateResult(getString(R.string.player_loses_a_turn, currentPlayer.getPlayerName()), currentPlayer)

                    //Switch to the next player.
                    currentPlayer = if(currentPlayer === player1) player2 else player1

                    updateStatus(getString(R.string.players_turn, currentPlayer.getPlayerName()), currentPlayer)
                }
                //Try for the jackpot.
                6-> {
                    tryingJackpot = true

                    updateResult(getString(R.string.player_rolls_again_for_the_jackpot, currentPlayer.getPlayerName()), currentPlayer)
                }
            }
        }

        binding.btnSubmit.setOnClickListener {
            val playerAnswer = binding.etPanel3.text.toString()

            if (validateInput(playerAnswer)) {
                if (computeAnswer(leftOperand, operator, rightOperand) == playerAnswer.toInt()) {
                    //Display a message when a player gets the correct answer.
                    updateResult(getString(R.string.player_answered_correctly, currentPlayer.getPlayerName()), currentPlayer)

                    if (tryingJackpot) {
                        tryingJackpot = false

                        //The player can not get double points with a jackpot.
                        currentPlayer.setMultiplier(1)

                        //The player receives all of the points in the jackpot.
                        currentPlayer.addPoints(jackpot)

                        //The jackpot resets to 5 points.
                        jackpot = 5
                    }
                    else {
                        currentPlayer.addPoints(questionValue)
                    }
                }
                else {
                    //Display a message when a player gets an incorrect answer.
                    updateResult(getString(R.string.player_answered_incorrectly, currentPlayer.getPlayerName()), currentPlayer)

                    //If the player answers a question incorrectly then the jackpot will increase by the question's value.
                    jackpot += questionValue
                    tryingJackpot = false
                }

                if (currentPlayer.getMultiplier() >= 2) {
                    currentPlayer.setMultiplier(1)
                }

                binding.llQuestion.visibility = View.INVISIBLE
                binding.etPanel3.text.clear()

                //Update scores and jackpot in the UI.
                updateScore()

                //First player to reach 20 points wins.
                if (currentPlayer.getPoints() >= 20) {
                    binding.tvStatus.text = getString(R.string.player_wins, currentPlayer.getPlayerName())
                    binding.tvResult.visibility = View.INVISIBLE
                    binding.btnPlayAgain.visibility = Button.VISIBLE
                }
                else {
                    currentPlayer = if (currentPlayer === player1) player2 else player1

                    updateStatus(getString(R.string.players_turn, currentPlayer.getPlayerName()), currentPlayer)
                }
            }
        }

        binding.btnPlayAgain.setOnClickListener{
            //Hide the Play Again button.
            binding.btnPlayAgain.visibility = Button.INVISIBLE

            //Reset the points for each player.
            player1.setPoints(0)
            player1.setMultiplier(1)

            player2.setPoints(0)
            player2.setMultiplier(1)

            //Reset the jackpot points.
            jackpot = 5

            //Decide which player goes first.
            currentPlayer = if(Random.nextInt(0,2) == 0) player1 else player2

            updateScore()

            //Display the current player's turn.
            updateStatus(getString(R.string.players_turn, currentPlayer.getPlayerName()), currentPlayer)
        }
    }

    private fun computeAnswer(leftOperand : Int, operator : Char, rightOperand : Int): Int {
        return when(operator){
            '+'-> leftOperand + rightOperand
            '-'-> leftOperand - rightOperand
            'x'-> leftOperand * rightOperand
            else-> -1
        }
    }

    private fun getDieFace(numberOfDots : Int): Int {
        return when (numberOfDots) {
            1-> R.drawable.die_face_1
            2-> R.drawable.die_face_2
            3-> R.drawable.die_face_3
            4-> R.drawable.die_face_4
            5-> R.drawable.die_face_5
            6-> R.drawable.die_face_6
            else-> -1
        }
    }

    private fun updateDisplay(questionType : String, player : Player){
        binding.tvQuestionType.text = if (player.getMultiplier() > 1 && !tryingJackpot) questionType.plus(getString(R.string.mulitplier_points, player.getMultiplier().toString())) else questionType

        binding.tvPanel1.text = leftOperand.toString()
        binding.tvOperator.text = operator.toString()
        binding.tvPanel2.text = rightOperand.toString()
        binding.btnRollDie.visibility = View.INVISIBLE

        binding.ivPanel1.setColorFilter(ContextCompat.getColor(this, player.getPlayerColor()), PorterDuff.Mode.MULTIPLY)
        binding.ivPanel2.setColorFilter(ContextCompat.getColor(this, player.getPlayerColor()), PorterDuff.Mode.MULTIPLY)
        binding.ivPanel3.setColorFilter(ContextCompat.getColor(this, player.getPlayerColor()), PorterDuff.Mode.MULTIPLY)
        binding.llQuestion.visibility = View.VISIBLE
    }

    private fun updateResult(resultText : String, player : Player){
        binding.tvResult.text = resultText
        binding.tvResult.setTextColor(ContextCompat.getColor(this, player.getPlayerColor()))
        binding.tvResult.visibility = View.VISIBLE
    }

    private fun updateScore(){
        //Display the jackpot.
        binding.tvJackpot.text = getString(R.string.jackpot_points, jackpot.toString())

        //Display the points for each player.
        binding.tvP1Score.text = getString(R.string.points, player1.getPoints().toString())
        binding.tvP2Score.text = getString(R.string.points, player2.getPoints().toString())
    }

    private fun updateStatus(statusText : String, player : Player){
        binding.tvStatus.text = statusText
        binding.tvStatus.setTextColor(ContextCompat.getColor(this, player.getPlayerColor()))
        binding.btnRollDie.visibility = Button.VISIBLE
    }

    private fun validateInput(playerAnswer: String?): Boolean {
        return if (playerAnswer.isNullOrEmpty()) {
            Toast.makeText(this, "Please enter an answer.", Toast.LENGTH_LONG).show()
            false
        }
        else {
            true
        }
    }
}