import java.awt.*;


public class Asteroid extends Entity{

    public Asteroid(GamePanel gp, ImageHandler img){
        this.gp = gp;
        this.img = img;
        SetDefaultValues();
        width = 64;
        height = 64;
    }

    public void SetDefaultValues() {
        double f = Math.random()/Math.nextDown(1.0);
        x = (int) (0*(1.0 - f) + gp.screenWidth*f);
        y = 0;
        speed = (int) (7*(1.0 - f) + 15*f);

    }

    public void update(){
        y += speed;
        // Hit Box
        hitbox = new Rectangle(GetX()+5, GetY(), width-8, height-4);
        hurtbox = new Rectangle(GetX()+5, GetY(), width-8, height-2);
    }
    public void draw (Graphics2D g2_rock){
//        g2_rock.setColor(Color.RED);
//        g2_rock.fillRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height); // hitbox debug
        g2_rock.drawImage(img.big_rock, x , y, width, height, null);

    }
}
