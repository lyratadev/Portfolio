package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TimerTask;

public class GameState extends Thread{
    private static CardTable view;
    private static CardGameOutline gameModel;
    private Timer timer;
    private int numPacksPerDeck = 0;
    private int numJokersPerPack = 0;
    private int numUnusedCardsPerPack = 0;
    private  Card[] unusedCardsPerPack = null;
    int playerTurn = 0;
    int counter = 0;
    static int NUM_CARDS_PER_HAND = 7;
    static int NUM_PLAYERS = 2;
    private int selectedCardIndex = -1;
    private int computerMulligen = 0;
    private int humanMulligen = 0;

    public void setComputerMulligen() {
        this.computerMulligen = this.computerMulligen + 1;
    }

    public void setHumanMulligen() {
        this.humanMulligen = this.humanMulligen + 1;
    }

    public int getHumanMulligen() {
        return humanMulligen;
    }

    public int getComputerMulligen() {
        return computerMulligen;
    }

    void repaintBoard() {
        view.renderComputerHandPanel();
        view.renderPlayerPanel();

        Card[] arrayOfHumanCards = new Card[gameModel.getHand(0).getNumCards()];
        int numberOfComputerCards = gameModel.getHand(1).getNumCards();
        ActionListener[] arrayOfActionCreatorForHuman = new ActionListener[gameModel.getHand(0).getNumCards()];


        view.renderHand( arrayOfHumanCards, arrayOfActionCreatorForHuman, numberOfComputerCards);
    }

    CardTable getView() {
        return view;
    }

    private void stopTimer() {
        timer.stopTimer();
    }

    private void startTimer() {
        timer = new Timer(view, counter, this);
        timer.start();
    }

    public void setCounter(int count) {
        counter = count;
    }

    public void cantPlay() throws InterruptedException {
        gameModel.simulateComputerTurn(true);
    }

    public void addButtonCantPlay() {
        JButton cantPlay = new JButton("I Can't Play...");
        cantPlay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cantPlay();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        view.getCounterButtons().add(cantPlay);
    }

    public void addStartStopButtons() {
        JButton stop = new JButton("Stop");
        JButton start = new JButton("Start");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopTimer();
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimer();
            }
        });
        view.getCounterButtons().add(start);
        view.getCounterButtons().add(stop);
    }

    GameState(int numPacksPerDeck, int numJokersPerPack, int numUnusedCardsPerPack, Card[] unusedCardsPerPack) {
        this.numJokersPerPack = numJokersPerPack;
        this.numPacksPerDeck = numPacksPerDeck;
        this.numUnusedCardsPerPack = numUnusedCardsPerPack;
        this.unusedCardsPerPack = unusedCardsPerPack;

        view = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
        addButtonCantPlay();
        addStartStopButtons();
        startTimer();

        gameModel = new CardGameOutline(
                numPacksPerDeck, numJokersPerPack,
                numUnusedCardsPerPack, unusedCardsPerPack,
                NUM_PLAYERS, NUM_CARDS_PER_HAND);
    }

    public void restartGame() {
        gameModel = new CardGameOutline(
                numPacksPerDeck, numJokersPerPack,
                numUnusedCardsPerPack, unusedCardsPerPack,
                NUM_PLAYERS, NUM_CARDS_PER_HAND);
        gameModel.deal();
        view.setVisible(true);
        view.repaint();
        repaintBoard(); //REPAINT BOARD with Present Game

    }

    public void start() {
        gameModel.deal();

        view.setSize(800, 600);
        view.setLocationRelativeTo(null);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setVisible(true);
        gameModel.initializeGame(view, this);
        view.setVisible(true);//SET BOARD VISIBLE
    }
}
