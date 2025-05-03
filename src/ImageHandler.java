import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageHandler {

    public BufferedImage staticPlayer, playerMove1, playerMove2, windowIcon, background, bit_of_big_rock, small_rock, big_rock, image = null, bullet, home_screen;
    public int spriteCounter = 0;
    public int spriteNum = 1;


    public void getImage(){
        try{
            home_screen = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/home_screen.png")));
            background = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/background.png")));
            windowIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/icons/Icon.png")));
            staticPlayer = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/staticPlayer.png")));
            playerMove1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/playerMove1.png")));
            playerMove2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/playerMove2.png")));
            bit_of_big_rock = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroids/small_rock.png")));
            small_rock = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroids/medium_rock.png")));
            big_rock = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroids/big_rock.png")));
            bullet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/asteroids/bullet.png")));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
