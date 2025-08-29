package com.shpp.p2p.cs.akoskovtsev.assignment3;

import com.shpp.cs.a.console.TextProgram;

import acm.util.RandomGenerator;

/**
 * Bernoulli Casino
 */

public class Assignment3Part5 extends TextProgram {

    /**
     * Lucky play a game until he reaches 20$ or more.
     */
    @Override
    public void run() {
        int lucky = 0;
        int countOfGames = 0;
        while (lucky < 20) {
            // count number of games.
            countOfGames++;
            // try to win money in one round.
            int moneyInOneRound = startGame();
            println("This game, you earned $" + moneyInOneRound);
            // total money wins.
            lucky += moneyInOneRound;
            println("Your total earning $" + lucky);
        }
        println("It took " + countOfGames + " games to earn $" + lucky);

    }

    /**
     * This method starts a game:
     * Sweaty makes a bet 1$,
     * Lucky  makes a spin while coin has an eagle(true)
     * and Sweaty makes doubles the bet
     * returns all the money that the sweaty bet.
     */
    private int startGame() {
        int sweaty = 1;
        // spin a coin while there is eagle(true)
        // and doubles the bet
        while (spinACoin()) {
            sweaty *= 2;
        }
        return sweaty;
    }

    /**
     * Method that takes a random boolean between true(eagle) and false(tail)
     *
     * @return - returns the boolean that randomized.
     */
    private boolean spinACoin() {
        RandomGenerator random = RandomGenerator.getInstance();
        return random.nextBoolean();
    }
}
