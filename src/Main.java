import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        // Object Instantiation
        JFrame window = new JFrame();
        ImageHandler img = new ImageHandler();
        GamePanel gamepanel = new GamePanel();

        // Window Setup
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Pew Pew Rocket Game");
        window.setIconImage(img.windowIcon);
        window.add(gamepanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // Start the Game

        gamepanel.startGameThread();
    }
}