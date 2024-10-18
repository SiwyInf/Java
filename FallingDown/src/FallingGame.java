import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class FallingGame extends JPanel implements ActionListener {
    private int heroX = 200;
    private int heroY = 50;
    private int velocityY = 0;
    private int velocityX = 0;
    private final int GRAVITY = 2;
    private final int JUMP_STRENGTH = -20;
    private final int MOVE_SPEED = 5;
    private ArrayList<Rectangle> obstacles = new ArrayList<>();
    private ArrayList<Rectangle> hpItems = new ArrayList<>();
    private Timer timer;
    private boolean gameOver = false;
    private JButton resetButton;
    private int score = 0;
    private int level = 1;
    private double obstacleSpeed = 8.0;
    private Random rand = new Random();
    private int lives = 3;
    private boolean invulnerable = false;
    private long invulnerableStartTime;
    private static final int INVULNERABLE_DURATION = 3000;
    private ArrayList<Rectangle> invulnerabilityItems = new ArrayList<>();
    private boolean invulnerableEffectActive = false;
    private long invulnerabilityStartTime;
    private static final int INVULNERABILITY_DURATION = 5000;
    private static int highScore = 0;
    private final int maxLives = 5;

    public FallingGame() {
        setLayout(null);
        timer = new Timer(20, this);
        timer.start();

        resetButton = new JButton("Reset");
        resetButton.setBounds(150, 300, 100, 50);
        resetButton.setVisible(false);
        resetButton.addActionListener(e -> resetGame());
        add(resetButton);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    jump();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    velocityX = -MOVE_SPEED;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    velocityX = MOVE_SPEED;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    velocityX = 0;
                }
            }
        });
        setFocusable(true);
    }

    private void jump() {
        if (!gameOver) {
            velocityY = JUMP_STRENGTH;
        }
    }

    private void generateNewObstacles() {
        int numberOfObstacles = rand.nextInt(3) + 1;
        int spaceBetween = 100;

        for (int i = 0; i < numberOfObstacles; i++) {
            int width = (score < 20) ? rand.nextInt(80) + 40 : rand.nextInt(80) + 40;
            int xPos = rand.nextInt(400 - width);
            obstacles.add(new Rectangle(xPos, 600 + (i * spaceBetween), width, 20));
        }

        if (score % 10 == 0 && score > 0 && rand.nextBoolean()) {
            int hpWidth = 30;
            int hpHeight = 30;
            int hpX = rand.nextInt(400 - hpWidth);
            boolean overlapsWithObstacles = false;
            for (Rectangle obstacle : obstacles) {
                if (obstacle.intersects(new Rectangle(hpX, 600, hpWidth, hpHeight))) {
                    overlapsWithObstacles = true;
                    break;
                }
            }
            if (!overlapsWithObstacles) {
                hpItems.add(new Rectangle(hpX, 600, hpWidth, hpHeight));
                System.out.println("Generated HP item at: " + hpX);
            }
        }

        if (score % 15 == 0 && score > 0) {
            int invulWidth = 30;
            int invulHeight = 30;
            int invulX = rand.nextInt(400 - invulWidth);
            boolean overlapsWithObstacles = false;
            for (Rectangle obstacle : obstacles) {
                if (obstacle.intersects(new Rectangle(invulX, 600, invulWidth, invulHeight))) {
                    overlapsWithObstacles = true;
                    break;
                }
            }
            if (!overlapsWithObstacles) {
                invulnerabilityItems.add(new Rectangle(invulX, 600, invulWidth, invulHeight));
                System.out.println("Generated invulnerability item at: " + invulX);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            if (score > highScore) {
                highScore = score;
            }
        }
        if (!gameOver) {
            velocityY += GRAVITY;
            heroY += velocityY;
            heroX += velocityX;

            if (heroX < 0) heroX = 0;
            if (heroX > 370) heroX = 370;

            if (heroY < 0) {
                heroY = 0;
                velocityY = 0;
            }

            if (heroY > 600) {
                gameOver = true;
                resetButton.setVisible(true);
                timer.stop();
            }

            for (Rectangle obstacle : obstacles) {
                obstacle.y -= obstacleSpeed;
            }
            for (Rectangle hpItem : hpItems) {
                hpItem.y -= obstacleSpeed;
            }
            for (Rectangle invulItem : invulnerabilityItems) {
                invulItem.y -= obstacleSpeed;
            }

            checkCollisions();

            if (obstacles.isEmpty() || obstacles.get(obstacles.size() - 1).y < 400) {
                generateNewObstacles();
            }

            if (!obstacles.isEmpty() && obstacles.get(0).y < -20) {
                obstacles.remove(0);
                score++;

                if (score % 20 == 0) {
                    level++;
                    obstacleSpeed += 0.1;
                }
            }

            if (!hpItems.isEmpty() && hpItems.get(0).y < -20) {
                hpItems.remove(0);
            }

            if (!invulnerabilityItems.isEmpty() && invulnerabilityItems.get(0).y < -20) {
                invulnerabilityItems.remove(0);
            }

            if (invulnerable && System.currentTimeMillis() - invulnerableStartTime > INVULNERABLE_DURATION) {
                invulnerable = false;
            }

            if (invulnerableEffectActive && System.currentTimeMillis() - invulnerabilityStartTime > INVULNERABILITY_DURATION) {
                invulnerableEffectActive = false;
                obstacleSpeed = 8.0;
            }
        }
        repaint();
    }

    private void checkCollisions() {
        for (Rectangle obstacle : obstacles) {
            if (obstacle.intersects(new Rectangle(heroX, heroY, 30, 30))) {
                if (!invulnerable) {
                    lives--;
                    if (lives <= 0) {
                        gameOver = true;
                        resetButton.setVisible(true);
                        timer.stop();
                    } else {
                        invulnerable = true;
                        invulnerableStartTime = System.currentTimeMillis();
                    }
                }
                return;
            }
        }

        for (Rectangle hpItem : hpItems) {
            if (hpItem.intersects(new Rectangle(heroX, heroY, 30, 30))) {
                hpItems.remove(hpItem);
                if (lives < maxLives) {
                    lives++;
                }
                return;
            }
        }
        for (Rectangle invulItem : invulnerabilityItems) {
            if (invulItem.intersects(new Rectangle(heroX, heroY, 30, 30))) {
                invulnerabilityItems.remove(invulItem);
                invulnerableEffectActive = true;
                invulnerabilityStartTime = System.currentTimeMillis();
                obstacleSpeed = 5.0;
                return;
            }
        }
    }

    private void resetGame() {
        heroX = 200;
        heroY = 50;
        velocityY = 0;
        velocityX = 0;
        gameOver = false;
        score = 0;
        lives = 3;
        level = 1;
        obstacles.clear();
        hpItems.clear();
        obstacleSpeed = 8.0;
        generateNewObstacles();
        resetButton.setVisible(false);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.CYAN);
        g.fillRect(0, 0, 400, 600);
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(50, 50, 100, 50);
        g.fillOval(100, 70, 120, 60);
        g.fillOval(300, 30, 150, 70);

        g.setColor(invulnerable ? new Color(255, 255, 0, 128) : Color.RED);
        g.fillRect(heroX, heroY, 30, 30);

        g.setColor(Color.GREEN);
        for (Rectangle obstacle : obstacles) {
            g.fillRect(obstacle.x, obstacle.y, obstacle.width, obstacle.height);
        }

        g.setColor(Color.YELLOW);
        for (Rectangle hpItem : hpItems) {
            int heartWidth = hpItem.width;
            int heartHeight = hpItem.height;
            g.fillRect(hpItem.x, hpItem.y, heartWidth, heartHeight);
        }

        g.setColor(Color.MAGENTA);
        for (Rectangle invulItem : invulnerabilityItems) {
            g.fillRect(invulItem.x, invulItem.y, invulItem.width, invulItem.height);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Wynik: " + score, 10, 20);
        g.drawString("Życia: " + lives, 10, 40);
        g.drawString("level: " + level, 10, 60);

        if (gameOver) {
            g.setColor(Color.black);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over!", 60, 250);
            g.drawString("Twój wynik: " + score, 20, 180);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Falling Game");
        FallingGame game = new FallingGame();
        frame.add(game);
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
