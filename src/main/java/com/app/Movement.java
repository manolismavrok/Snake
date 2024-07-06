package com.app;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Movement extends Menu implements KeyListener {  
    public static final int head = 0;  
    private int clocks = 70;
    private boolean right = true, left = false, down = false, up = false, lost = false;
    private boolean alive;
    private ScheduledExecutorService executorRight = Executors.newScheduledThreadPool(1),
            executorLeft = Executors.newScheduledThreadPool(1),
            executorDown = Executors.newScheduledThreadPool(1),
            executorUp = Executors.newScheduledThreadPool(1),
            executorOver = Executors.newScheduledThreadPool(1);

    public Movement() throws IOException {     
        playBtn.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {                                
                try {                  
                    start();
                } catch (IOException ex) {
                    Logger.getLogger(Movement.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        menuBtn.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                try {
                    reset();
                } catch (IOException ex) {
                    Logger.getLogger(Movement.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT && right == false && left == false) {
            turnRight();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && left == false && right == false) {
            turnLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && down == false && up == false) {
            turnDown();
        } else if (e.getKeyCode() == KeyEvent.VK_UP && up == false && down == false) {
            turnUp();
        }

    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }

    public void turnRight() {
        executorLeft.shutdown();
        executorDown.shutdown();
        executorUp.shutdown();
        Runnable rightRunnable = new Runnable() {
            @Override
            public void run() {
                if (snakeInBounds()) {
                    snake.moveRight();
                    checkCollision();
                } else {
                    gameOver();
                }
            }
        };
        executorRight = Executors.newScheduledThreadPool(1);
        executorRight.scheduleAtFixedRate(rightRunnable, 0, clocks, TimeUnit.MILLISECONDS);
        right = true;
        left = false;
        up = false;
        down = false;        
    }

    public void turnLeft() {
        executorRight.shutdown();
        executorDown.shutdown();
        executorUp.shutdown();
        Runnable leftRunnable = new Runnable() {
            @Override
            public void run() {
                if (snakeInBounds()) {
                    snake.moveLeft();
                    checkCollision();
                } else {
                    gameOver();
                }
            }
        };
        executorLeft = Executors.newScheduledThreadPool(1);
        executorLeft.scheduleAtFixedRate(leftRunnable, 0, clocks, TimeUnit.MILLISECONDS);
        right = false;
        left = true;
        up = false;
        down = false;        
    }

    public void turnDown() {
        executorRight.shutdown();
        executorLeft.shutdown();
        executorUp.shutdown();
        Runnable downRunnable = new Runnable() {
            @Override
            public void run() {
                if (snakeInBounds()) {
                    snake.moveDown();
                    checkCollision();
                } else {
                    gameOver();
                }
            }
        };
        executorDown = Executors.newScheduledThreadPool(1);
        executorDown.scheduleAtFixedRate(downRunnable, 0, clocks, TimeUnit.MILLISECONDS);
        right = false;
        left = false;
        down = true;
        up = false;
    }

    public void turnUp() {
        executorRight.shutdown();
        executorLeft.shutdown();
        executorDown.shutdown();
        Runnable upRunnable = new Runnable() {
            @Override
            public void run() {
                if (snakeInBounds()) {
                    snake.moveUp();
                    checkCollision();
                } else {
                    gameOver();
                }
            }
        };
        executorUp = Executors.newScheduledThreadPool(1);
        executorUp.scheduleAtFixedRate(upRunnable, 0, clocks, TimeUnit.MILLISECONDS);
        right = false;
        left = false;
        down = false;
        up = true;
    }

    public void start() throws IOException {
        reset();
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
        Runnable rightRunnable = new Runnable() {
            public void run() {
                if ((snake.getBodyPartX(head) > -1 || snake.getBodyPartX(head) < 691 || snake.getBodyPartY(head) > -1 || snake.getBodyPartY(head) < 511) && lost == false) {
                    snake.moveRight();
                } else {
                    gameOver();
                }
            }
        };
        executorRight = Executors.newScheduledThreadPool(1);
        executorRight.scheduleAtFixedRate(rightRunnable, 0, clocks, TimeUnit.MILLISECONDS);
        right = true;
        left = false;
        down = false;
        up = false;
        lost = false;       
    }

    public void reset() throws IOException {
        executorRight.shutdown();
        executorLeft.shutdown();
        executorDown.shutdown();
        executorUp.shutdown();
        snake.getTail().clear();
        snake.setCounter(1);
        snake.spawn();
        frame.removeKeyListener(this);
    }

    public void gameOver() {
        gameLost.setVisible(true);
        right = true;
        left = false;
        down = false;
        up = false;
        lost = true;
        executorRight.shutdown();
        executorLeft.shutdown();
        executorDown.shutdown();
        executorUp.shutdown();
        executorOver.shutdown();
        frame.removeKeyListener(this);
    }

    public boolean snakeInBounds() {
        if (!(snake.getBodyPartX(head) < 0 || snake.getBodyPartX(head) > 690 || snake.getBodyPartY(head) < 0 || snake.getBodyPartY(head) > 510) && lost == false) {
            alive = true;
        } else {
            alive = false;
        }

        return alive;
    }

    public void checkCollision() {
        for (int i = 1; i < snake.getTail().size(); i++) {
            if (snake.getBodyPartX(head) == snake.getBodyPartX(i) && snake.getBodyPartY(head) == snake.getBodyPartY(i)) {
                gameOver();
            }
        }
    }

}
