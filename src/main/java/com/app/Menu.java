package com.app;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

public class Menu {

    public JFrame frame = new JFrame("MySnake");
    private ImagePanel panel = new ImagePanel(getResource("snakeBg.png"));
    private JPanel menuPanel = new JPanel();
    private JLabel label = new JLabel(new ImageIcon(Main.class.getClassLoader().getResource("mysnakelogo.gif")));
    private JLabel creditsText = new JLabel("Manolis Mavrokoukoulakis");
    public JLabel gameLost = new JLabel(new ImageIcon(getResource("gameOver.png")));
    public JToggleButton playBtn = new JToggleButton();
    private JButton contactBtn = new JButton();
    public JToggleButton menuBtn = new JToggleButton("Menu");
    private JToggleButton creditsBtn = new JToggleButton();
    private GridBagConstraints grid = new GridBagConstraints();
    public Snake snake = new Snake();

    public Menu() throws IOException {
        //Labels      
        label.setLayout(null);
        label.setBounds(175, 50, 350, 100);
        label.repaint();
        gameLost.setLayout(null);
        gameLost.setBounds(175, 100, 350, 100);
        gameLost.setVisible(false);
        gameLost.repaint();

        //Buttons     
        //Play Button
        playBtn.setIcon(new ImageIcon(getResource("playButton.png")));
        playBtn.setContentAreaFilled(false);
        playBtn.setFocusPainted(false);
        playBtn.setBorderPainted(true);
        playBtn.setToolTipText("Play");
        playBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.remove(label);
                frame.remove(panel);
                menuBtn.setContentAreaFilled(false);
                menuBtn.setFocusPainted(false);
                menuBtn.setBorderPainted(true);
                menuBtn.setToolTipText("Menu");
                menuBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        snake.resetScore();
                        snake.getScoreLabel().setText("Score: " + snake.getScore());
                        gameLost.setVisible(false);
                        frame.remove(snake);
                        frame.remove(menuPanel);
                        frame.add(label);
                        frame.add(panel);
                        frame.repaint();
                    }
                });               
                menuPanel.setBackground(Color.lightGray);
                menuPanel.add(menuBtn);
                menuPanel.add(snake.getScoreLabel());                
                frame.add(snake);
                frame.add(menuPanel, BorderLayout.PAGE_END);
                frame.repaint();
                frame.validate();               
            }
        });

        //Contact Button
        contactBtn.setIcon(new ImageIcon(getResource("contactUs.png")));
        contactBtn.setContentAreaFilled(false);
        contactBtn.setFocusPainted(false);
        contactBtn.setBorderPainted(true);
        contactBtn.setToolTipText("Contact Us");
        contactBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://www.instagram.com/manolis_mavrok/?hl=el"));
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //Credits Button
        creditsBtn.setIcon(new ImageIcon(getResource("credits.png")));
        creditsBtn.setContentAreaFilled(false);
        creditsBtn.setFocusPainted(false);
        creditsBtn.setBorderPainted(true);
        creditsBtn.setToolTipText("Credits");
        creditsBtn.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                int state = itemEvent.getStateChange();
                if (state == ItemEvent.SELECTED) {
                    creditsText.setVisible(true);
                } else {
                    creditsText.setVisible(false);
                }
            }
        });

        //Credits Text
        creditsText.setLayout(null);
        creditsText.setLocation(500, 500);
        creditsText.repaint();
        creditsText.setVisible(false);

        //Build panel
        panel.setLayout(new GridBagLayout());
        panel.setSize(720, 600);
        grid.insets = new Insets(2, 2, 2, 2);
        grid.gridy = 1;
        grid.ipadx = -30;
        grid.ipady = -6;
        panel.add(playBtn, grid);
        grid.gridy = 2;
        grid.ipadx = -90;
        grid.ipady = -65;
        panel.add(contactBtn, grid);
        grid.gridy = 3;
        grid.ipadx = -30;
        grid.ipady = -10;
        panel.add(creditsBtn, grid);
        panel.add(creditsText);

        //Build frame
        //716x596
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("snake.png"));
        frame.add(label);
        frame.add(gameLost);
        frame.add(panel);
        frame.setSize(716, 596);
        frame.setMinimumSize(new Dimension(716, 596));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

     private static BufferedImage getResource(String fileName) throws IOException {
        ClassLoader classLoader = Main.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return ImageIO.read(inputStream);
        }
    }

}
