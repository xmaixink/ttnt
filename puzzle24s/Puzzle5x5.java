package puzzle24s;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class Puzzle5x5 implements ActionListener {
    JFrame fr;
    JPanel mainPanel;
    JPanel buttonPanel;
    JButton[][] button;
    JButton solveButton, shuffleButton;
    int rows;
    int cols;
    JLabel[][] label;
    static int[][] board;

    public Puzzle5x5() {
        rows = 5;
        cols = 5;
        board = new int[rows][cols];
        initGUI();
    }

    public void initGUI() {
        fr = new JFrame("24 Puzzle Game");
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new GridLayout(5, 5));
        button = new JButton[rows][cols];
        label = new JLabel[rows][cols];
        // call the default board
        this.defaultBoard();

        // according to board default data, set image on button
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                button[i][j] = new JButton();
                String text = i + "," + j; // store the index i and j
                button[i][j].setText(text);
                button[i][j].setFont(new Font("TimesRoman", Font.PLAIN, 0));
                button[i][j].addActionListener(this);
                int val = board[i][j];
                String fileName;
                if (val != -1) {
                    fileName = "./puzzle24s/Pic5x5/" + val + ".png";
                    label[i][j] = new JLabel(new ImageIcon(fileName), JLabel.CENTER);
                } else {
                    label[i][j] = new JLabel("");
                }
                button[i][j].add(label[i][j]);
                button[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 2));
                button[i][j].setBackground(Color.LIGHT_GRAY);
                mainPanel.add(button[i][j]);
            }
        }

        // Add the shuffle and solve buttons
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        solveButton = new JButton("Solve");
        shuffleButton = new JButton("Shuffle");
        solveButton.addActionListener(this);
        shuffleButton.addActionListener(this);
        buttonPanel.add(shuffleButton);
        buttonPanel.add(solveButton);

        fr.add(mainPanel);
        fr.add(buttonPanel, "South");
        fr.setVisible(true);
        fr.setSize(500, 500);
        fr.setLocationRelativeTo(null);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void defaultBoard() {
        int count = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (count <= 24) {
                    board[i][j] = count++;
                } else {
                    board[i][j] = -1;
                }
            }
        }
    }

    public void shuffleBoard() {
        Random rand = new Random();
        int[] array = new int[25];
        for (int i = 0; i < 25; i++) {
            array[i] = i + 1;
        }
        array[24] = -1;
        for (int i = 0; i < 25; i++) {
            int index = rand.nextInt(25);
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = array[count];
                count = count + 1;
            }
        }
        updateBoard();
    }

    Boolean isWin() {
        int count = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] != count && board[i][j] != -1) {
                    return false;
                }
                count = count + 1;
            }
        }
        return true;
    }

    public void displayWinMsg() {
        JFrame frame = new JFrame("Game Win");
        JLabel label = new JLabel("You Solve The Puzzle", JLabel.CENTER);
        label.setFont(new Font("TimesRoman", Font.BOLD, 20));
        frame.add(label);
        frame.setLayout(new GridLayout(1, 1));
        frame.setSize(300, 300);
        frame.setBackground(Color.white);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    @Override
public void actionPerformed(ActionEvent ae) {
    if (ae.getSource() == solveButton) {
        SolveWorker worker = new SolveWorker(board, this); 
        worker.execute();
    } else if (ae.getSource() == shuffleButton) {
        shuffleBoard();
    } else {
        Boolean flag = isWin();
        if (!flag) {
            String s = ae.getActionCommand();
            int r = Integer.parseInt(s.split(",")[0]);
            int c = Integer.parseInt(s.split(",")[1]);
            if (board[r][c] != -1) {
                if (r + 1 < rows && board[r + 1][c] == -1) {
                    label[r][c].setIcon(new ImageIcon(""));
                    String fileName = "./puzzle24s/Pic5x5/" + board[r][c] + ".png";
                    label[r + 1][c].setIcon(new ImageIcon(fileName));
                    int temp = board[r][c];
                    board[r][c] = board[r + 1][c];
                    board[r + 1][c] = temp;
                } else if (r - 1 >= 0 && board[r - 1][c] == -1) {
                    label[r][c].setIcon(new ImageIcon(""));
                    String fileName = "./puzzle24s/Pic5x5/" + board[r][c] + ".png";
                    label[r - 1][c].setIcon(new ImageIcon(fileName));
                    int temp = board[r][c];
                    board[r][c] = board[r - 1][c];
                    board[r - 1][c] = temp;
                } else if (c + 1 < cols && board[r][c + 1] == -1) {
                    label[r][c].setIcon(new ImageIcon(""));
                    String fileName = "./puzzle24s/Pic5x5/" + board[r][c] + ".png";
                    label[r][c + 1].setIcon(new ImageIcon(fileName));
                    int temp = board[r][c];
                    board[r][c] = board[r][c + 1];
                    board[r][c + 1] = temp;
                } else if (c - 1 >= 0 && board[r][c - 1] == -1) {
                    label[r][c].setIcon(new ImageIcon(""));
                    String fileName = "./puzzle24s/Pic5x5/" + board[r][c] + ".png";
                    label[r][c - 1].setIcon(new ImageIcon(fileName));
                    int temp = board[r][c];
                    board[r][c] = board[r][c - 1];
                    board[r][c - 1] = temp;
                }
            }
            flag = isWin();
            if (flag) {
                displayWinMsg();
            }
        }
    }
}

    void updateBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int val = board[i][j];
                String fileName;
                if (val != -1) {
                    fileName = "./puzzle24s/Pic5x5/" + val + ".png";
                    label[i][j].setIcon(new ImageIcon(fileName));
                } else {
                    label[i][j].setIcon(new ImageIcon(""));
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Testing");
        new Puzzle5x5();
    }

    public JFrame getFrame() {
        return fr;
    }
}
