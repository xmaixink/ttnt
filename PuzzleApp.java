import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import puzzle15.Puzzle4x4;
import puzzle24s.Puzzle5x5;
import puzzle8.Puzzle3x3;

public class PuzzleApp {
    private JFrame frame;
    private JFrame currentGameFrame;
    private BackgroundPanel mainPanel;
    private JButton eightPuzzleButton;
    private JButton fifteenPuzzleButton;
    private JButton twentyfourPuzzleButton;

    public static void main(String[] args) {
        try {
            // Set Nimbus Look and Feel
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        EventQueue.invokeLater(() -> {
            try {
                PuzzleApp window = new PuzzleApp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public PuzzleApp() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 500); // Adjusted to square size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Puzzle App");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(60, 63, 65));  // Dark background color

        JLabel lblHeader = new JLabel("Nhóm 2 Trí Tuệ Nhân Tạo");
        lblHeader.setForeground(Color.WHITE);  // White text color
        lblHeader.setFont(new Font("Arial", Font.BOLD, 14)); // Font for the header
        lblHeader.setHorizontalAlignment(JLabel.LEFT);
        topPanel.add(lblHeader, BorderLayout.WEST);  // Add label to the left

        frame.getContentPane().add(topPanel, BorderLayout.NORTH);

        // Use BackgroundPanel for the main panel
        mainPanel = new BackgroundPanel("./background.jpg");
        mainPanel.setLayout(new GridBagLayout());
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER); // Changed layout

        JLabel lblChooseGame = new JLabel("Chọn Game Để Chơi");
        lblChooseGame.setForeground(Color.WHITE);  // White text color
        lblChooseGame.setFont(new Font("Arial", Font.BOLD, 24)); 
        GridBagConstraints gbc_lblChooseGame = new GridBagConstraints();
        gbc_lblChooseGame.insets = new Insets(30, 0, 30, 0); 
        gbc_lblChooseGame.gridx = 0;
        gbc_lblChooseGame.gridy = 0;
        mainPanel.add(lblChooseGame, gbc_lblChooseGame);

        Dimension buttonSize = new Dimension(200, 50); // Set preferred size for buttons

        eightPuzzleButton = new JButton("8-Puzzle");
        eightPuzzleButton.setFont(new Font("Arial", Font.PLAIN, 20)); 
        eightPuzzleButton.setPreferredSize(buttonSize);
        eightPuzzleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openNewGame(new Puzzle3x3().getFrame());
            }
        });
        GridBagConstraints gbc_eightPuzzleButton = new GridBagConstraints();
        gbc_eightPuzzleButton.insets = new Insets(0, 0, 20, 0); 
        gbc_eightPuzzleButton.gridx = 0;
        gbc_eightPuzzleButton.gridy = 1;
        mainPanel.add(eightPuzzleButton, gbc_eightPuzzleButton);

        fifteenPuzzleButton = new JButton("15-Puzzle");
        fifteenPuzzleButton.setFont(new Font("Arial", Font.PLAIN, 20));
        fifteenPuzzleButton.setPreferredSize(buttonSize);
        fifteenPuzzleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openNewGame(new Puzzle4x4().getFrame());
            }
        });
        GridBagConstraints gbc_fifteenPuzzleButton = new GridBagConstraints();
        gbc_fifteenPuzzleButton.insets = new Insets(0, 0, 20, 0); 
        gbc_fifteenPuzzleButton.gridx = 0;
        gbc_fifteenPuzzleButton.gridy = 2;
        mainPanel.add(fifteenPuzzleButton, gbc_fifteenPuzzleButton);

        twentyfourPuzzleButton = new JButton("24-Puzzle");
        twentyfourPuzzleButton.setFont(new Font("Arial", Font.PLAIN, 20)); 
        twentyfourPuzzleButton.setPreferredSize(buttonSize);
        twentyfourPuzzleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openNewGame(new Puzzle5x5().getFrame());
            }
        });
        GridBagConstraints gbc_twentyfourPuzzleButton = new GridBagConstraints();
        gbc_twentyfourPuzzleButton.insets = new Insets(0, 0, 20, 0); // Increased padding
        gbc_twentyfourPuzzleButton.gridx = 0;
        gbc_twentyfourPuzzleButton.gridy = 3;
        mainPanel.add(twentyfourPuzzleButton, gbc_twentyfourPuzzleButton);
    }

    private void openNewGame(JFrame newGameFrame) {
        if (currentGameFrame != null && currentGameFrame.isVisible()) {
            int option = JOptionPane.showConfirmDialog(null, "Bạn có muốn đóng trò chơi hiện tại để mở trò chơi mới không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                currentGameFrame.dispose(); // Đóng cửa sổ trò chơi hiện tại nếu người dùng chấp nhận
            } else {
                return; // Không mở trò chơi mới nếu người dùng không chấp nhận
            }
        }
        currentGameFrame = newGameFrame;
        currentGameFrame.setVisible(true);
    }

    // BackgroundPanel class for setting background image
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            this.backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 500); // Square preferred size
        }
    }
}
