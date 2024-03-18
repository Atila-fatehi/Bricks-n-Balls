package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

public class GamePrepPage extends JFrame {
    Color color = Color.WHITE;
    public GamePrepPage() {

        repaint();

        getContentPane().setBackground(new Color(0xA6C8EA));
        setTitle("Brick Breaker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 900);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);

        JLabel label = new JLabel("Choose Difficulty : ");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setBounds(200, 10, 300, 50);
        label.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label.setForeground(new Color(0x002A5A));
        add(label);


        JCheckBox easy = new JCheckBox("Easy");
        easy.setBounds(70, 70, 200, 50);
        easy.setFocusable(false);
        easy.setBackground(new Color(0xA6C8EA));
        easy.setForeground(new Color(0x002A5A));
        easy.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 20));
        easy.setSelected(true);
        add(easy);

        JCheckBox medium = new JCheckBox("Medium");
        medium.setBounds(270, 70, 200, 50);
        medium.setFocusable(false);
        medium.setBackground(new Color(0xA6C8EA));
        medium.setForeground(new Color(0x002A5A));
        medium.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 20));
        medium.setSelected(true);
        add(medium);


        JCheckBox hard = new JCheckBox("Hard");
        hard.setBounds(470, 70, 200, 50);
        hard.setFocusable(false);
        hard.setBackground(new Color(0xA6C8EA));
        hard.setForeground(new Color(0x002A5A));
        hard.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 20));
        hard.setSelected(true);
        add(hard);

        ButtonGroup group = new ButtonGroup();
        group.add(easy);
        group.add(medium);
        group.add(hard);

        ImageIcon x = new ImageIcon(Paths.get("").toAbsolutePath() + "\\src\\images\\red-x-icon.png");
        Image img = x.getImage();
        Image newimg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        easy.setIcon(newIcon);
        medium.setIcon(newIcon);
        hard.setIcon(newIcon);

        ImageIcon check = new ImageIcon(Paths.get("").toAbsolutePath() + "\\src\\images\\green-checkmark-icon.png");
        img = check.getImage();
        newimg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon2 = new ImageIcon(newimg);
        easy.setSelectedIcon(newIcon2);
        medium.setSelectedIcon(newIcon2);
        hard.setSelectedIcon(newIcon2);


        JLabel label2 = new JLabel("Choose Ball's Color : ");
        label2.setHorizontalAlignment(JLabel.CENTER);
        label2.setHorizontalTextPosition(JLabel.CENTER);
        label2.setBounds(200, 260, 300, 50);
        label2.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label2.setForeground(new Color(0x002A5A));
        add(label2);

        JButton button3 = new JButton("Choose");
        button3.setBounds(200, 320, 300, 50);
        button3.setFocusable(false);
        button3.setHorizontalAlignment(JButton.CENTER);
        button3.setHorizontalTextPosition(JButton.CENTER);
        button3.setBackground(new Color(0x002A5A));
        button3.setForeground(new Color(0x7AB2E1));
        button3.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        add(button3);

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JColorChooser colorChooser = new JColorChooser();
                color = JColorChooser.showDialog(null , "Pick a color" , Color.WHITE);
                repaint();
            }
        });

        JLabel label3 = new JLabel("Enter Your Name : ");
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setHorizontalTextPosition(JLabel.CENTER);
        label3.setBounds(200, 460, 300, 50);
        label3.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label3.setForeground(new Color(0x002A5A));
        add(label3);

        JTextField textField = new JTextField();
        textField.setBounds(200 , 520 , 300 , 50);
        textField.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        textField.setBackground(new Color(0xFFFFFF));
        textField.setHorizontalAlignment(0);
        add(textField);

        JButton button2 = new JButton("Start Game");
        button2.setBounds(200, 710, 300, 50);
        button2.setFocusable(false);
        button2.setHorizontalAlignment(JButton.CENTER);
        button2.setHorizontalTextPosition(JButton.CENTER);
        button2.setBackground(new Color(0x002A5A));
        button2.setForeground(new Color(0x7AB2E1));
        button2.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        add(button2);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                // new StartPage();
            }
        });

        JButton button1 = new JButton("Back");
        button1.setBounds(200, 770, 300, 50);
        button1.setFocusable(false);
        button1.setHorizontalAlignment(JButton.CENTER);
        button1.setHorizontalTextPosition(JButton.CENTER);
        button1.setBackground(new Color(0x002A5A));
        button1.setForeground(new Color(0x7AB2E1));
        button1.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        add(button1);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new StartPage();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(color);
        g.fillOval(550,310, 50 ,50);

        g.dispose();
    }
}
