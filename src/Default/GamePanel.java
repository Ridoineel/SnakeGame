package Default;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

/* This game has two mode, 1 and 2, identified by <<mode>> variable */

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 150;
    int mode = 1;
    int x[];
    int y[];
    int bodyParts;
    int applesEaten;
    int appleX, appleY;
    char direction;
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        super();
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapater());

        startGame();
    }

    public void startGame() {
        // initializations
        x = new int[GAME_UNITS];
        y = new int[GAME_UNITS];
        bodyParts = 6;
        direction = 'R';
        applesEaten = 0;
        running = true;

        newApple();

        if (timer == null) {
            timer = new Timer(DELAY, this);
        }

        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            // draw verticals lines and horizontals lines
            //for (int i = 0; i < Math.max(SCREEN_HEIGHT, SCREEN_WIDTH) / UNIT_SIZE; i++) {
                //g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                //g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            //}

            // draw apple
            g.setColor(Color.blue);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            // draw snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(Color.gray);
                    // g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                }

                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }else {
            // draw diagonals lines
             for (int i = 0; i < Math.max(SCREEN_HEIGHT, SCREEN_WIDTH) / UNIT_SIZE; i++) {
                 g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE + SCREEN_WIDTH, SCREEN_HEIGHT);
                 g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH - i * UNIT_SIZE, SCREEN_HEIGHT);
             }

             gameOver(g);
        }

        // Print score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "))/2, 20);
    }

    public void newApple() {
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] -= UNIT_SIZE;
                break;
            case 'D':
                y[0] += UNIT_SIZE;
                break;
            case 'R':
                x[0] += UNIT_SIZE;
                break;
            case 'L':
                x[0] -= UNIT_SIZE;
                break;
            default:
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            applesEaten++;
            bodyParts++;
            newApple();
        }
    }

    public void checkCollisions() {
        for(int i = bodyParts; i > 0; i--) {
            // when snake bite same him
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }

            if (mode == 1) {
                // when head touch vertical walls
                if (x[0]  < 0 || x[0] > SCREEN_WIDTH) {
                    running = false;
                }

                // when head touch horizontal walls
                if (y[0] < 0 || y[0] > SCREEN_HEIGHT) {
                    running = false;
                }

                if (!running) {
                    timer.stop();
                }

            }else{
                if (x[0] < 0) {
                    x[0] = SCREEN_WIDTH;
                }
                if (x[0] > SCREEN_WIDTH) {
                    x[0] = 0;
                }

                if (y[0] < 0) {
                    y[0] = SCREEN_HEIGHT;
                }
                if (y[0] > SCREEN_HEIGHT) {
                    y[0] = 0;
                }
            }
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        g.drawString("GAME OVER", (SCREEN_WIDTH - metrics.stringWidth("GAME OVER"))/2, SCREEN_HEIGHT/2);

        g.setColor(Color.GRAY);
        g.setFont(new Font("Ink Free", Font.BOLD, 15));
        metrics = g.getFontMetrics(g.getFont());
        g.drawString("Press enter to replay \uD83D\uDE01️.", (SCREEN_WIDTH - metrics.stringWidth("Press enter to replay."))/2, 75 + SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }

        repaint();
    }

    public class MyKeyAdapater extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    startGame();
                    break;
            }
        }
    }

}
