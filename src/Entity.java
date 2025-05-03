import java.awt.*;

public class Entity {

    GamePanel gp;
    KeyHandler keyH;
    ImageHandler img;
    Player player;


    public int x, y, width, height;
    public int speed;
    public Rectangle hitbox;
    public Rectangle hurtbox;

    public int GetX(){
        return x;
    }
    public int GetY(){
        return y;
    }



}
