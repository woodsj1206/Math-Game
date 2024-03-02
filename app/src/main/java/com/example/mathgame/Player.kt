package com.example.mathgame

class Player (name: String = "Player", color : Int = 0){
    private val playerName: String = name
    private val playerColor: Int = color
    private var points : Int = 0
    private var multiplier : Int = 1

    fun addPoints(points : Int){
        this.points += multiplier * points
    }

    fun getMultiplier() : Int {
        return multiplier
    }

    fun getPlayerName() : String {
        return playerName
    }

    fun getPlayerColor() : Int {
        return playerColor
    }

    fun getPoints(): Int {
        return this.points
    }

    fun setMultiplier(multiplier : Int){
        this.multiplier = multiplier
    }

    fun setPoints(points: Int){
        this.points = points
    }

}