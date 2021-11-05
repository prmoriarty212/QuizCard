/**
QuizCardBuilder App written in the course of training
on the book Head First Java, 2nd edition.

Also in the process of writing code, recommendations of
the Checkstyle plugin were used, with Sun Checks Rules
*/

package com.quizbuilder.app;

import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class QuizCardBuilder {


    private JTextArea question;
    private JTextArea answer;
    private ArrayList<QuizCard> cardList;
    private JFrame frame;

    /**
     * run our builder and method go()
     * @param args
     */
     public static void main(final String[] args) {
        QuizCardBuilder builder = new QuizCardBuilder();
        builder.go();
    }

    /**
     * Building GUI
     */
    private void go() {
        frame = new JFrame("Quiz Card Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        final Font bigFont = new Font("sanserif",
                Font.BOLD,
                24);
        question = new JTextArea(6, 20);
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(bigFont);

        JScrollPane qScroller = new JScrollPane(question);
        qScroller.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        answer = new JTextArea(6, 20);
        answer.setLineWrap(true);
        answer.setFont(bigFont);

        JScrollPane aScroller = new JScrollPane(answer);
        aScroller.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        aScroller.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        JButton nextButton = new JButton("Next Card");

        cardList = new ArrayList();

        JLabel qLabel = new JLabel("Question");
        JLabel aLabel = new JLabel("Answer");

        mainPanel.add(qLabel);
        mainPanel.add(qScroller);
        mainPanel.add(aLabel);
        mainPanel.add(aScroller);
        mainPanel.add(nextButton);
        nextButton.addActionListener(new NextCardListener());

        /**
         * We create an object JMenuBar and add a menu "File" with items "New" and "Save".
         * Then we tell the main panel what it should use JMenuBar.
         * Menu items can launch ActionEvent
         */
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        newMenuItem.addActionListener(new NewMenuListener());

        saveMenuItem.addActionListener(new SaveMenuListener());
        fileMenu.add(newMenuItem);
        fileMenu.add(saveMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(500, 600);
        frame.setVisible(true);
    }
    public class NextCardListener implements ActionListener {
        /**
         * Add the current card to list and clear the fields.
         * @param ev
         */
        public void actionPerformed(final ActionEvent ev) {
            QuizCard card = new QuizCard(question.getText(), answer.getText());
            cardList.add(card);
            clearCard();
        }
    }
    public class SaveMenuListener implements ActionListener {
        /**
         * a dialog box is called and the program stops at this line until
         * the user selects the Save menu
         * @param ev
         */
        public void actionPerformed(final ActionEvent ev) {
            QuizCard card = new QuizCard(question.getText(), answer.getText());
            cardList.add(card);
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            saveFile(fileSave.getSelectedFile());
        }
    }
    public class NewMenuListener implements ActionListener {
        /**
         * clear the text fields and list of cards.
         * @param ev
         */
        public void actionPerformed(final ActionEvent ev) {
            cardList.clear();
            clearCard();
        }
    }
    private void clearCard() {
        question.setText("");
        answer.setText("");
        question.requestFocus();
    }

    /**
     * A method that directly writes to the file
     * (called by the event handler of the SaveMenuListener class).
     * The argument is a File object that is saved by the user.
     *
     * We combine BufferedReader with new fileWriter for more efficient writing.
     * @param file
     */
    public void saveFile(final File file) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            /**
             * run through the ArrayList with cards and write them one by one per line,
             * separating the question and answer with a character "/" and at the end
             * add the newline character "\n".
             */
            for (QuizCard card:cardList) {
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("couldn`t write the cardLIst out");
            ex.printStackTrace();
        }
    }

    /**
     * QuizCard class for dividing data on cards.
     */
    final class QuizCard {
        private String question;
        private String answer;
        private QuizCard(final String q, final String a) {
            question = q;
            answer = a;
        }
        public String getQuestion() {
            return question;
        }
        public String getAnswer() {
            return  answer;
        }
    }
}

