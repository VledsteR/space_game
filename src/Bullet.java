import java.awt.*;

public class Bullet extends Entity {
    public Bullet (GamePanel gp, ImageHandler img, Player player, java.util.List<Asteroid> asteroid){
        this.gp = gp;
        this.player = player;
        this.img = img;
        speed = 8;
        width = 2;
        height = 6;
        SetDefaultValues();
    }


    public void SetDefaultValues() {
        x = player.GetX()+player.width/2;
        y = player.GetY();
    }

    public void update(){
        y -= speed;
        hitbox = new Rectangle(GetX(), GetY(), width, height);
    }

    public void draw ( Graphics2D g2){
//        g2.setColor(Color.BLUE);
//        g2.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);    // hitbox debugging
        g2.drawImage(img.bullet, x, y, width, height, null);
    }
}
