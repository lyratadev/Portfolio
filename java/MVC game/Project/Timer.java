package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

class Timer extends Thread {
    private int seconds = 0;
    CardTable view;
    CardGameOutline model;
    char givenValue;
    boolean doNotSleep = true;
    boolean setBackground = false;
    boolean playerDoesntHaveCard = false;
    GameState game;



    void simulateTurn() throws InterruptedException {
        view.getPnlComputerHand().setBackground(Color.BLUE);
        view.getPnlComputerHand().repaint();
        boolean cardHasBeenTakenFromComputer = false;
        char cardInRotationValue = model.getHand(1).inspectTopCard().getValue();

        for (int j = 2; j < 5; j += 1) {
            if (!cardHasBeenTakenFromComputer &&
                    Card.checkIfCardValueIsOneLevelHigherOrLower(cardInRotationValue, model.getHand(j).inspectTopCard().getValue())) {

                Thread.sleep(1000);
                view.getPnlComputerHand().setBackground(Color.WHITE);
                view.getPnlComputerHand().add(new JLabel("I win this round"));
                Thread.sleep(2000);


                view.getPnlComputerHand().repaint();

                model.takeTurn(1, 0, j);
                cardHasBeenTakenFromComputer = true;
            }
        }
        if (!cardHasBeenTakenFromComputer) {
            Thread.sleep(1000);
            view.getPnlComputerHand().setBackground(Color.WHITE);
            view.getPnlComputerHand().add(new JLabel("I Lost this round"));
            Thread.sleep(2000);
            view.getPnlComputerHand().repaint();
            if (!playerDoesntHaveCard) {
                model.refresh();
            }

            if (playerDoesntHaveCard) {
                view.getPnlComputerHand().add(new JLabel("We need to get 3 new cards..."));
                Thread.sleep(2000);
                view.getPnlComputerHand().repaint();
                model.dealStack();

            }

        }
    }

    synchronized void doNothing() throws InterruptedException {
        if (setBackground) {
            simulateTurn();
        }
        if (!setBackground) {
            do {
                try {
                    Thread.sleep(1000);
                    seconds += 1;
                    view.renderCounterPanel();
                    view.getCounterArea().add(new JLabel(String.valueOf(seconds)));
                    view.getCounterArea().revalidate();
                    view.getCounterArea().repaint();
                    game.setCounter(seconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (doNotSleep);
        }

    }

    @Override
    public void run() {

        try {
            doNothing();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    Timer(CardTable view, int counter, GameState game) {
        this.view = view;
        this.seconds = counter;
        this.game = game;
    }

    Timer(CardGameOutline model, CardTable view, boolean playerDoesntHaveCard ) {
        this.model = model;
        this.setBackground = true;
        this.view = view;
        this.playerDoesntHaveCard = playerDoesntHaveCard;
    }

    public void stopTimer() {
        doNotSleep = false;
    }
}
