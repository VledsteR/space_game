import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class GamePanel extends JPanel implements Runnable{

    // Instantiating objects
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    ImageHandler img = new ImageHandler();
    Player player = new Player(this, keyH, img);
    private final List<Asteroid> asteroids = new ArrayList<>();
    private final List <Bullet> bullets = new ArrayList<>();

    // Game Panel setup
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    // Screen Settings
    final int screenWidth = 1024;
    final int screenHeight = 640;

    gameState state = gameState.MENU;

    // FPS
    int FPS = 50000;
    int APS = 30;

    // Game Start
    public void startGameThread() {
       gameThread = new Thread(this);
       gameThread.start();
    }

    // variable declaration
    double drawInterval = (double) 1000000000 / FPS; // divides a second (represented in nanoseconds) to the fps desired
    double actionInterval = (double) 1000000000 / APS;
    double deltaActions = 0, deltaRender = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;  // for fps check
    double FPS_timer = 0;
    int drawCount = 0; // for fps check
    int displayFPS = 0;
    int score = 0;
    int high_score = loadHighScore();
    long total_time_taken = 0;
    long time_taken = 0;

    @Override
    public void run() {

        // getting the image files
        img.getImage();

        // game loop
        while (gameThread != null) {
            GameLoop();
        }
    }

    public void GameLoop(){

        currentTime = System.nanoTime();

        //math magic to make setting a fps limit possible
        deltaActions += (currentTime - lastTime) / actionInterval;
        deltaRender += (currentTime - lastTime) / drawInterval;
        timer += (currentTime - lastTime);
        FPS_timer += (currentTime - lastTime);
        lastTime = currentTime;
        total_time_taken = timer;

            switch (state)
            {
                case MENU:
                    if(deltaActions >= 1){
                        updateGameState();
                        deltaActions = 0;
                    }
                    if(deltaRender >= 1) { repaint(); deltaRender = 0; drawCount++; }
                    if(timer >= 1000000000) { timer = 0; drawCount = 0; }
                    if (FPS_timer/1000> 2998200) { displayFPS = drawCount; FPS_timer = 0; }
                    break;

                case GAME:
                    if(deltaActions >= 1)
                    {
                        updateGameState();
                        update();
                        checkCollisions();
                        deltaActions = 0;
                    }
                    if(deltaRender >= 1) { repaint(); deltaRender = 0; drawCount++; }
                    if(timer >= 300000000) { timer = 0; drawCount = 0; addAsteroid(); }
                    if (FPS_timer/1000> 2998200) { displayFPS = drawCount; FPS_timer = 0; }
                    break;

                case PAUSE:
                    if(deltaActions >= 1) { updateGameState(); deltaActions = 0; }
                    if(deltaRender >= 1) { repaint(); deltaRender = 0; drawCount++;}
                    if(timer >= 1000000000) { timer = 0; drawCount = 0; }
                    if (FPS_timer/1000> 2998200) { displayFPS = drawCount; FPS_timer = 0; }
                    break;
                case DEAD:
                    if(deltaActions >= 1) { updateGameState(); deltaActions = 0; }
                    if(deltaRender >= 1) { repaint(); deltaRender = 0; drawCount++;}
                    if(timer >= 1000000000) { timer = 0; drawCount = 0; }
                    if (FPS_timer/1000> 2998200) { displayFPS = drawCount; FPS_timer = 0; }
                    break;

            }

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        switch(state){
            case MENU:
                g2.drawImage(img.home_screen, 0, 0, screenWidth, screenHeight, null);
                break;
            case GAME:
                g2.drawImage(img.background, 0, 0, screenWidth, screenHeight, null);

                for(Asteroid asteroid : asteroids) {
                    asteroid.draw(g2);
                }
                player.draw(g2);
                for(Bullet bullet : bullets){
                    bullet.draw(g2);
                }
                g2.setColor(Color.white);
                g2.drawString("FPS: " + displayFPS, 100, 100);
                g2.drawString("Score: " + score, screenWidth - 150, 100);
                g2.drawString("High Score: " + high_score, screenWidth - 150, 150);

                break;

            case PAUSE:
                g2.setColor(Color.white);
                g2.drawString("Options", 50, 50);
                break;

            case DEAD:
                g2.setColor(Color.white);
                g2.drawString("You crashed!", screenWidth/2, screenHeight/2);
        }
        g2.dispose();
    }
    public void updateGameState () {
        switch(state){
            case MENU :
                if(keyH.enterPressed)
                    state = gameState.GAME;
                break;

            case GAME:
                if(keyH.escPressed && !keyH.pressed) {
                    state = gameState.PAUSE;
                    keyH.pressed = true;
                }
                break;

            case PAUSE:
                if (keyH.escPressed && !keyH.pressed) {
                    state = gameState.GAME;
                    keyH.pressed = true;
                }
                break;

            case DEAD:
                if(keyH.enterPressed && !keyH.pressed) {
                    state = gameState.MENU;
                    keyH.pressed = true;
                }
                break;
        }
    }
    public void update(){
        switch (state)
        {
            case GAME:
                player.update();
                for(Asteroid asteroid : asteroids) {
                    asteroid.update();
                }
                for (Bullet bullet: bullets){
                    bullet.update();
                }
                break;
            case PAUSE:
                break;
        }

    }
    public void checkCollisions() {

        for(int b = 0; b < asteroids.size(); b++){
            Asteroid a = asteroids.get(b);
            if(player.hurtbox.intersects(a.hitbox))
            {
                asteroids.clear();
                player.setDefaultValues();
                bullets.clear();
                if(score >= high_score) {
                    saveHighScore(score , total_time_taken);
                }
                score = 0;
                high_score = loadHighScore();
                time_taken = loadTotal_time_taken();
                state = gameState.DEAD;
            }
        }
        for(int a = 0; a < bullets.size(); a++){
            Bullet b = bullets.get(a);
            for(int c = 0; c < asteroids.size(); c++) {
                Asteroid asteroid1 = asteroids.get(c);
                if(b.hitbox.intersects(asteroid1.hurtbox))
                {
                    asteroids.remove(asteroid1);
                    bullets.remove(b);
                    score += 100;
                }
            }
        }

    }
    public void addAsteroid(){
        asteroids.add(new Asteroid(this, img));
    }
    public void addBullets(){
        bullets.add(new Bullet(this, img, player, asteroids));
    }
    public void saveHighScore(long score, long total_time_taken) {
        try (FileWriter writer = new FileWriter("highscore.txt")) {             //
            writer.write(Long.toString(score));                              //      used chatgpt for this
        } catch (IOException e) {                                               //
            System.out.println("Error saving high score: " + e.getMessage());   //
        }
        try(FileWriter writer1 = new FileWriter("total_time_taken.txt")){
            writer1.write(Long.toString(total_time_taken));
        } catch (IOException e) {
            System.out.println("Error saving total_time_taken: " + e.getMessage());
        }
    }
    public int loadHighScore(){
        File file = new File("highscore.txt");                                  //
        try (Scanner scanner = new Scanner(file)) {                             //
            return scanner.nextInt();                                           //      chatgpt for this as well
        } catch (FileNotFoundException e) {                                     //
            // File doesn't exist yet; return 0 as default high score           //
            return 0;                                                           //
        } catch (Exception e) {                                                 //
            System.out.println("Error reading high score: " + e.getMessage());  //
            return 0;                                                           //
        }
    }
    public long loadTotal_time_taken(){
        File file = new File("total_time_taken.txt");                           //
        try (Scanner scanner = new Scanner(file)) {                             //
            return scanner.nextInt();                                           //      chatgpt for this as well
        } catch (FileNotFoundException e) {                                     //
            // File doesn't exist yet; return 0 as default high score           //
            return 0;                                                           //
        } catch (Exception e) {                                                 //
            System.out.println("Error reading total time taken: " + e.getMessage());
        }
            return 0;                                                           //
        }
}
