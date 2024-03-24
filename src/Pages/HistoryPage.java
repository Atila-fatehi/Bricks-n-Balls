package Pages;

import DataBase.data;
import Music.MusicPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HistoryPage extends JFrame {
    ArrayList<data> history = new ArrayList<>();
    public HistoryPage(MusicPlayer musicPlayer) {
        getContentPane().setBackground(new Color(0xA6C8EA));
        setTitle("History");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);


        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("HelveticaNeue-CondensedBlack" , Font.BOLD , 15));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(50 , 50 , 400 ,500);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        File fileSave = new File(Paths.get("").toAbsolutePath() + "\\src\\DataBase\\history.ser");
        try (FileInputStream fileIn = new FileInputStream(fileSave);
             ObjectInputStream objIn = new ObjectInputStream(fileIn)) {
            history = (ArrayList<data>) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {

        }
        for (int i = 0; i < history.size(); i++) {
            textArea.append("name : " + history.get(i).getPlayerName() + "    " + "score : " +history.get(i).getScore() + "    " + history.get(i).getDateTime() + "\n");
        }

        getContentPane().add(scrollPane);


        JButton button1 = new JButton("Back");
        button1.setBounds(100, 570, 300, 50);
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
                new StartPage(musicPlayer);
            }
        });
    }
}
