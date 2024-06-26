![Logo](app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.webp)

# Math-Game
This application has been developed with Kotlin for Project 1 of  CIS 436.

## Overview
Math-Game is a simple two-player application designed to help children practice arithmetic on a single device.

## Game Instructions
### Objective: 
The objective of the game is for players to earn points by correctly answering math problems. 

### Gameplay: 
#### Rolling the Die
Players take turns rolling the die.
<br/>

![Screenshot](https://github.com/woodsj1206/Math-Game/blob/main/assets/screenshot_player_1_roll_dice.png)
<br/>

Each face of the die corresponds to an action listed below:

| Die Face  | Action |
| ------------- | ------------- |
| 1  | Addition problem |
| 2  | Subtraction problem |
| 3  | Multiplication problem |
| 4  | Roll again for double points  |
| 5  | Lose a turn  |
| 6  | Try for the jackpot  |

#### Types of Problems
This game involves solving addition, subtraction, and multiplication problems. 
<br/>

![Screenshot](https://github.com/woodsj1206/Math-Game/blob/main/assets/screenshot_player_1_subtraction_problem.png)
<br/>

Whenever a player successfully answers a math problem, they will earn points based on the value assigned to the problem. Each type of math problem is limited to a specific range of numbers. Below is a table that lists the problem types along with their values and corresponding number ranges:

| Type of Problem | Value | Ranges |
| ------------- | ------------- | ------------- |
| Addition  | 1 | 0 - 99 |
| Subtraction  | 2 | 0 - 99 |
| Multiplication  | 3 | 0 - 20 |

*NOTE: A wrong answer will add the question's value to the jackpot.*
<br/>



### Goal:
The goal of the game is to be the first player to reach 20 points.
<br/>

![Screenshot](https://github.com/woodsj1206/Math-Game/blob/main/assets/screenshot_player_1_wins.png)

## Development Environment
- IDE: Android Studio Hedgehog | 2023.1.1 Patch 2
- Language: Kotlin
