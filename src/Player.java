import java.awt.*;
public class Player extends Entity{

    public Player(GamePanel gp, KeyHandler keyH, ImageHandler img){
        this.gp = gp;
        this.keyH = keyH;
        this.img = img;
        setDefaultValues();

        speed = 8;
        width = 80;
        height = 64;
    }

    public void setDefaultValues() {
        x = gp.screenWidth/2 - 32;
        y = gp.screenHeight - 100;
    }
    public void update(){

        // Movement
        if(keyH.shiftkeyHeld)
        {
            speed = 20;
        }
        else {
            speed = 8;
        }
            if (keyH.upPressed)
                y -= speed;
            if (keyH.downPressed)
                y += speed;
            if (keyH.leftPressed)
                x -= speed;
            if (keyH.rightPressed)
                x += speed;
            if(keyH.spacePressed && !keyH.pressed) {
                gp.addBullets();
                keyH.pressed = true;
            }

        //Setting movement border bounds
        if(x < 0)                  {x = 1;}
        if(x+80 >= gp.screenWidth)  {x = gp.screenWidth-80;}
        if(y < 0)                  {y = 1;}
        if(y+64 >= gp.screenHeight) {y = gp.screenHeight-64;}

        // Collision
            // Hurt Box
            hurtbox = new Rectangle(GetX()+10, GetY()+5, width-20, height-10);


        // Animation
        img.spriteCounter++;
        if(img.spriteCounter > 6) {
            if (img.spriteNum == 1) {img.spriteNum = 2;}
            else if (img.spriteNum == 2) {img.spriteNum = 1;}
            img.spriteCounter = 0;
        }
    }

    public void draw (Graphics2D g2) {

        //this makes sure the animations only play when they should
        if (keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed)
        {
            if (img.spriteNum == 1) {
                img.image = img.playerMove1;
            }
            if (img.spriteNum == 2) {
                img.image = img.playerMove2;
            }
        }
        else {
            img.image = img.staticPlayer;
        }
//g2.fillRect(hurtbox.x, hurtbox.y, hurtbox.width, hurtbox.height);
       g2.drawImage(img.image, x, y, 80, 80, null);
    }

}
