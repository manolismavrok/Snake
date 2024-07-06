package com.app;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class Snake extends JComponent {
    private int x, y;
    private int tempx, tempy;
    private int spawnLength = 4;
    private int randomX = ThreadLocalRandom.current().nextInt(300, 420);
    private int randomY = ThreadLocalRandom.current().nextInt(240, 360);
    private int i = randomX - (randomX % 10);
    private int j = randomY - (randomY % 10);
    private int dim = 10;
    private int steps = 10;
    private int counter = 1;
    private int score = 0;
    public ArrayList<Snake> tail = new ArrayList<Snake>();
    private JLabel scoreLabel = new JLabel("Score: " + getScore());

    public Snake() throws IOException {

    }

    public Snake(int x, int y) throws IOException {
        this.x = x;
        this.y = y;
    }    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);   
        
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing for smooth circle edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Set rendering quality hints for better visual quality
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

        drawSnake(g2d, tail.get(0).x, tail.get(0).y);
        drawFood(g2d, i, j);    
        try {
            foodEaten(g2d, tail.get(0).x, tail.get(0).y);
        } catch (IOException e) {
            e.printStackTrace();
        }       
        for (Snake snake : tail) {
            drawSnake(g2d, snake.x, snake.y);
        };
    }

    public void drawSnake(Graphics g, int x, int y) {
        g.setColor(Color.WHITE);
        g.drawOval(x, y, dim, dim);
        g.setColor(Color.BLACK);
        g.fillOval(x, y, dim-1, dim-1);
    }

    public void drawFood(Graphics g, int i, int j) {
        g.setColor(Color.WHITE);
        g.drawOval(i, j, dim, dim);
        g.setColor(Color.RED);
        g.fillOval(i, j, dim-1, dim-1);
    }

    public void spawn() throws IOException {   
        Snake snake;
        for (int i = 0; i < spawnLength; i++) {
            snake = new Snake(x, y);
            tail.add(snake);
            tail.get(i).x = 10 + (steps * (spawnLength - 1)) - (steps * i);
            tail.get(i).y = 10;
        }
        repaint();
    }

    public void moveRight() {
        tempx = tail.get(0).x;
        tempy = tail.get(0).y;

        tail.get(0).x = tail.get(0).x + steps;

        if (tail.size() > 1) {
            tail.get(tail.size() - counter).x = tempx;
            tail.get(tail.size() - counter).y = tempy;
            counter++;
        }

        if (counter >= tail.size()) {
            counter = 1;
        }
        
        repaint();
    }

    public void moveLeft() {
        tempx = tail.get(0).x;
        tempy = tail.get(0).y;

        tail.get(0).x = tail.get(0).x - steps;

        if (tail.size() > 1) {
            tail.get(tail.size() - counter).x = tempx;
            tail.get(tail.size() - counter).y = tempy;
            counter++;
        }

        if (counter >= tail.size()) {
            counter = 1;
        }

        repaint();
    }

    public void moveDown() {
        tempx = tail.get(0).x;
        tempy = tail.get(0).y;

        tail.get(0).y = tail.get(0).y + steps;

        if (tail.size() > 1) {
            tail.get(tail.size() - counter).x = tempx;
            tail.get(tail.size() - counter).y = tempy;
            counter++;
        }

        if (counter >= tail.size()) {
            counter = 1;
        }

        repaint();
    }

    public void moveUp() {
        tempx = tail.get(0).x;
        tempy = tail.get(0).y;

        tail.get(0).y = tail.get(0).y - steps;

        if (tail.size() > 1) {
            tail.get(tail.size() - counter).x = tempx;
            tail.get(tail.size() - counter).y = tempy;
            counter++;
        }

        if (counter >= tail.size()) {
            counter = 1;
        }

        repaint();
    }

    private void foodEaten(Graphics g, int x, int y) throws IOException {
        if (tail.get(0).x == i && tail.get(0).y == j) {
            foodSpawn();
            setScore();
            scoreLabel.setText("Score: " + getScore());
            Snake snake = new Snake(x, y);
            tail.add(snake);
            counter++;
            repaint();
        }
    }

    private void foodSpawn() {
        randomX = ThreadLocalRandom.current().nextInt(50, 670);
        randomY = ThreadLocalRandom.current().nextInt(50, 500);
        this.i = randomX - (randomX % 10);
        this.j = randomY - (randomY % 10);
    }

    public void resetScore() {
        this.score = 0;
    }

    private void setScore() {
        this.score = score + 1;
    }

    public int getScore() {
        return score;
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getBodyPartX(int bodyPart) {
        return tail.get(bodyPart).x;
    }

    public int getBodyPartY(int bodyPart) {
        return tail.get(bodyPart).y;
    }

    public ArrayList<Snake> getTail() {
        return this.tail;
    }

}
