package Pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class SettingPage extends JFrame {
    File file;
    PrintWriter printWriter;
    boolean aim;
    boolean music;
    boolean saveToHistory;

    public SettingPage() throws FileNotFoundException {
        file = new File(Paths.get("").toAbsolutePath() + "\\src\\DataBase\\settings.txt");
        if (file.exists()) {
            Scanner scanner = new Scanner(file);
            aim = scanner.nextLine().equals("1");
            music = scanner.nextLine().equals("1");
            saveToHistory = scanner.nextLine().equals("1");
        } else {
            printWriter = new PrintWriter(file);
            printWriter.println("1");
            printWriter.println("1");
            printWriter.println("1");
            printWriter.flush();
            printWriter.close();
            aim = true;
            music = true;
            saveToHistory = true;
        }
        getContentPane().setBackground(new Color(0xA6C8EA));
        setTitle("Setting");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);

        JCheckBox checkBox1 = new JCheckBox("Aim");
        checkBox1.setBounds(120, 100, 300, 50);
        checkBox1.setFocusable(false);
        checkBox1.setBackground(new Color(0xA6C8EA));
        checkBox1.setForeground(new Color(0x002A5A));
        checkBox1.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        checkBox1.setSelected(aim);
        checkBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    save(checkBox1.isSelected(), music, saveToHistory);
                    aim = !aim;
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JCheckBox checkBox2 = new JCheckBox("Theme Music");
        checkBox2.setBounds(120, 200, 300, 50);
        checkBox2.setFocusable(false);
        checkBox2.setBackground(new Color(0xA6C8EA));
        checkBox2.setForeground(new Color(0x002A5A));
        checkBox2.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        checkBox2.setSelected(music);
        checkBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    save(aim, checkBox2.isSelected(), saveToHistory);
                    music = !music;
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JCheckBox checkBox3 = new JCheckBox("Save to History");
        checkBox3.setBounds(120, 300, 350, 50);
        checkBox3.setFocusable(false);
        checkBox3.setBackground(new Color(0xA6C8EA));
        checkBox3.setForeground(new Color(0x002A5A));
        checkBox3.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        checkBox3.setSelected(saveToHistory);
        checkBox3.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    save(aim, music, checkBox3.isSelected());
                    saveToHistory = !saveToHistory;
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        ImageIcon x = new ImageIcon(Paths.get("").toAbsolutePath() + "\\src\\images\\red-x-icon.png");
        Image img = x.getImage();
        Image newimg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        checkBox1.setIcon(newIcon);
        checkBox2.setIcon(newIcon);
        checkBox3.setIcon(newIcon);

        ImageIcon check = new ImageIcon(Paths.get("").toAbsolutePath() + "\\src\\images\\green-checkmark-icon.png");
        img = check.getImage();
        newimg = img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon2 = new ImageIcon(newimg);
        checkBox1.setSelectedIcon(newIcon2);
        checkBox2.setSelectedIcon(newIcon2);
        checkBox3.setSelectedIcon(newIcon2);

        add(checkBox1);
        add(checkBox2);
        add(checkBox3);

        JButton button1 = new JButton("Back");
        button1.setBounds(100, 500, 300, 50);
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

    public void save(boolean a, boolean b, boolean c) throws FileNotFoundException {
        printWriter = new PrintWriter(file);
        if (a) {
            printWriter.println("1");
        } else {
            printWriter.println("0");
        }
        if (b) {
            printWriter.println("1");
        } else {
            printWriter.println("0");
        }
        if (c) {
            printWriter.println("1");
        } else {
            printWriter.println("0");
        }
        printWriter.flush();
        printWriter.close();
    }
}
