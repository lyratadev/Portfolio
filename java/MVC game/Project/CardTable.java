package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// VIEW
@SuppressWarnings("serial")
class CardTable extends JFrame {
    //VARIABLES
    static String WIN_BANTER = "Well well well..I win this round.";
    static String LOSE_BANTER = "Oh well, can't play this turn!";
    static String NO_ONE_WINS_BANTER = "Well seems like we both don't have it.";
    static int MAX_CARDS_PER_HAND = 56;
    static int MAX_PLAYERS = 2; // for now, we only allow 2 person games
    private int numCardsPerHand;
    private int numPlayers;
    public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea, counterArea, counterButtons, pnlCounterFrame, blankPanel, cardsRemaining;


    //CONSTRUCTOR
    CardTable(String title, int numCardsPerHand, int numPlayers) {
        this.setTitle(title);


        GridLayout layout = new GridLayout(2, 3); //ROWS // COLUMNS
        //    LayoutManager boxLayout = new BoxLayout(pnlPlayArea, BoxLayout.PAGE_AXIS);
        FlowLayout fLayout = new FlowLayout(FlowLayout.CENTER);


        pnlComputerHand = new JPanel(); //panel for computer hand
        pnlHumanHand = new JPanel();    //panel for player hand
        pnlPlayArea = new JPanel();    // panel for play area

        //TIMER PANEL
        pnlCounterFrame = new JPanel();
        counterButtons = new JPanel();
        counterArea = new JPanel();    //panel for timer
        cardsRemaining = new JPanel();
        blankPanel = new JPanel();     //blank panel

        //     pnlComputerHand.setLayout(x1Layout);
        // counterArea.setPreferredSize(new Dimension(100, 100));

        //    counterButtons.setPreferredSize(new Dimension(100, 100));

        blankPanel.setPreferredSize(new Dimension(200, 200));
        pnlCounterFrame.setPreferredSize(new Dimension(200, 200));
        counterArea.setPreferredSize(new Dimension(200, 100));
        pnlCounterFrame.setLayout(layout);


        this.counterArea.setBorder(BorderFactory.createTitledBorder("Time Elapsed"));
        this.pnlComputerHand.setBorder(BorderFactory.createTitledBorder("Computer hand"));
        this.pnlPlayArea.setBorder(BorderFactory.createTitledBorder("Play Area"));
        this.pnlHumanHand.setBorder(BorderFactory.createTitledBorder("Your hand"));

        //this.setLayout(layout);

        this.setLayout(new BorderLayout());
        //this.pnlPlayArea.setLayout(fLayout);
        this.add(pnlComputerHand, BorderLayout.NORTH);
        this.add(pnlPlayArea, BorderLayout.CENTER);
        this.add(pnlHumanHand, BorderLayout.SOUTH);
        this.add(blankPanel, BorderLayout.WEST);
        this.add(pnlCounterFrame, BorderLayout.EAST);

        this.pnlCounterFrame.setLayout(layout);
        pnlCounterFrame.setLayout(new BorderLayout());
        pnlCounterFrame.add(counterButtons, BorderLayout.CENTER);
        pnlCounterFrame.add(counterArea, BorderLayout.NORTH);

        cardsRemaining.setBorder(BorderFactory.createTitledBorder("Cards Remaining"));
        pnlCounterFrame.add(cardsRemaining, BorderLayout.SOUTH);

    }

    //METHODS
    public void renderPlayerPanel() {
        pnlHumanHand.removeAll();
        pnlHumanHand.revalidate();
        pnlHumanHand.repaint();
    }

    public JPanel getCounterButtons() {
        return counterButtons;
    }

    public void renderCounterPanel() {
        counterArea.removeAll();
        counterArea.revalidate();
        counterArea.repaint();
    }

    public JPanel getCounterArea() {
        return counterArea;
    }

    public void renderComputerHandPanel() {
        pnlComputerHand.removeAll();
        pnlComputerHand.revalidate();
        pnlComputerHand.repaint();
    }

    public void renderPlayAreaPanel() {
        pnlPlayArea.removeAll();
        pnlPlayArea.revalidate();
        pnlPlayArea.repaint();
    }

    public void renderComputerMessage(int choice) {
        // 1 = win, 2 = lose, 3 = tie
        String messageVal = choice == 1 ? WIN_BANTER : choice == 2 ? LOSE_BANTER : NO_ONE_WINS_BANTER;
        JLabel message = new JLabel(messageVal);
        getPnlComputerHand().add(message);
        repaint();
    }

    public JPanel getPnlComputerHand() {
        return pnlComputerHand;
    }

    public JPanel getPnlHumanHand() {
        return pnlHumanHand;
    }

    public JPanel getPnlPlayArea() {
        return pnlPlayArea;
    }

    public int getNumCardsPerHand() {
        return numCardsPerHand;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void updateCardInDeck(int cardsRemainingValue) {
        cardsRemaining.removeAll();
        cardsRemaining.revalidate();
        cardsRemaining.repaint();
        cardsRemaining.setBorder(BorderFactory.createTitledBorder("Cards Remaining"));
        JLabel remainingInt = new JLabel(String.valueOf(cardsRemainingValue));
        cardsRemaining.add(remainingInt);
        System.out.println(cardsRemainingValue);
        pnlCounterFrame.add(cardsRemaining, BorderLayout.SOUTH);
        cardsRemaining.repaint();

    }

    public void showError() {
        JLabel error = new JLabel("That doesn't work...try again.");
        getPnlPlayArea().add(error);
        getPnlPlayArea().repaint();
    }

    public void refreshView(Card[] middleStack, ActionListener[] listenersToRender, Card humanCard, Card computerCard) {
        renderComputerHandPanel();
        renderPlayerPanel();
        renderPlayAreaPanel();

        JButton[] buttonArray = new JButton[3];
        JLabel[] iconArray = new JLabel[3];

        JLabel computerCardLabel = new JLabel(GUICard.getIcon(computerCard));
        JLabel humanCardLabel = new JLabel(GUICard.getIcon(humanCard));

        for (int b = 0; b < middleStack.length; b++) {
            JButton humanLabel = new JButton(GUICard.getIcon(middleStack[b]));
            humanLabel.addActionListener(listenersToRender[b]);
            getPnlPlayArea().add(humanLabel);
        }


        getPnlComputerHand().add(computerCardLabel);
        getPnlHumanHand().add(humanCardLabel);
        getPnlComputerHand().repaint();
        getPnlHumanHand().repaint();
    }

    public void renderHand(Card[] cardsToRender, ActionListener[] listenersToRender, int amountOfCardsForComputer) {
        JButton[] computerLabels = new JButton[amountOfCardsForComputer];
        JButton[] humanLabels = new JButton[cardsToRender.length];

        for (int b = 0; b < cardsToRender.length; b++) {
            JButton humanLabel = new JButton(GUICard.getIcon(cardsToRender[b]));

            humanLabel.addActionListener(listenersToRender[b]);

            humanLabels[b] = humanLabel;

        }

        for (int i = 0; i < amountOfCardsForComputer; i += 1) {
            computerLabels[i] = new JButton(GUICard.getIconBack());
        }

        for (int a = 0; a < cardsToRender.length; a += 1) {
            getPnlHumanHand().add(humanLabels[a]);
            getPnlComputerHand().add(computerLabels[a]);
        }
    }
}
















