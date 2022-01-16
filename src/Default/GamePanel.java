package Default;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX, appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        super();
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.red);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapater());

        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        for (int  i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT/UNIT_SIZE);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH/UNIT_SIZE, i * UNIT_SIZE);
        }
    }


    public void newApple() {

    }

    public void paint(Graphics g) {

    }

    public void move() {

    }

    public void checkApple() {

    }


    public void checkCollisions() {

    }

    public void gameOver() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //
    }

    public class MyKeyAdapater extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

        }
    }

}
