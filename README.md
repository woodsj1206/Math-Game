# Math-Game
This application has been developed with Kotlin for Project 1 of  CIS 436.

## Overview
Math-Game is a simple two-player application designed to help children practice arithmetic on a single device.

## Game Instructions
### Objective: 
The objective of the game is for players to earn points by correctly answering math problems. 

### Gameplay: 
#### Rolling the Die
Players take turns rolling the die. Each face of the die corresponds to an action listed below:

| Die Face  | Action |
| ------------- | ------------- |
| 1  | Addition problem |
| 2  | Subtraction problem |
| 3  | Multiplication problem |
| 4  | Roll again for double points  |
| 5  | Lose a turn  |
| 6  | Try for the jackpot  |

#### Type of Problems
This game involves solving addition, subtraction, and multiplication problems. Whenever a player successfully answers a math problem, they will earn points based on the value assigned to the problem. Each type of math problem is limited to a specific range of numbers. Below is a table that lists the problem types along with their values and corresponding number ranges:

| Type of Problem | Value | Ranges |
| ------------- | ------------- | ------------- |
| Addition  | 1 | 0 - 99 |
| Subtraction  | 2 | 0 - 99 |
| Multiplication  | 3 | 0 - 20 |

*NOTE: A wrong answer will add the question's value to the jackpot.*

### Goal:
The goal of the game is to be the first player to reach 20 points.

