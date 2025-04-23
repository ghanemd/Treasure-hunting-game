import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity{

    GamePanel gamePanel ;
    KeyHandler keyHandler ;

    public final int screenX ;
    public final int screenY ;
    int nbKeys ;

    public Player (GamePanel gamePanel , KeyHandler keyHandler)
    {
        this.gamePanel = gamePanel ;
        this.keyHandler = keyHandler ;

        screenX = gamePanel.screenWidth/2 - (gamePanel.tileSize/2) ;
        screenY = gamePanel.screenHeight/2 - (gamePanel.tileSize/2) ;

        solidArea = new Rectangle();    //rectangle a bit smaller than the player 
        solidArea.x = 8 ;
        solidArea.y = 16 ;
        solidAreaDefaultX = solidArea.x ;
        solidAreaDefaultY = solidArea.y ;
        solidArea.width = 32 ;
        solidArea.height = 32 ;

        setDefaultValues();
        getImagePlayer();
        direction = "down" ;
    }

    public void setDefaultValues()
    {
        this.worldX = gamePanel.tileSize * 23 ;
        this.worldY = gamePanel.tileSize * 21  ;
        this.speed = 4 ;
    }

    public void getImagePlayer ()
    {
        try
        {
            up1 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/assets/player/boy_right_2.png"));

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void update()   
    {
        if(keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed)
        {
            if (keyHandler.upPressed){
                direction = "up" ;
            }
            if (keyHandler.downPressed){
                direction = "down" ;
            }
            if (keyHandler.leftPressed){
                direction = "left" ;
            }
            if(keyHandler.rightPressed){
                direction = "right" ;
            }

            // CHECK TILE COLLISION
            collisionOn = false ;
            gamePanel.collisionChecker.checkTile(this);

            //CHECK OBJECT COLLISION 
            int objIndex = gamePanel.collisionChecker.checkObject(this, true) ;
            pickUpObject(objIndex);

            // IF THERE IS NO COLLISION , THE PLAYER CAN MOVE 

            if (!collisionOn){
                switch (direction) {
                    case "up" :  this.worldY -= speed ;  break ;
                    case "down" : this.worldY += speed ;  break ;
                    case "left" :  this.worldX -= speed ;  break ;
                    case "right" :  this.worldX += speed ;  break ;
                    default:  break;
                }
            }     
        } 
        spriteCounter++;
        if (spriteCounter > 10){
            spriteNumber = (spriteNumber == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    public void draw (Graphics2D g2)
    {
        BufferedImage image = null ;
        switch (direction) {
            case "up":
                if(spriteNumber == 1){
                    image = up1 ;
                }
                if (spriteNumber == 2){
                    image = up2 ;
                }
                break;
            case "down":
                if (spriteNumber == 1){
                    image = down1 ; 
                }
                if (spriteNumber == 2){
                    image = down2 ; 
                }
                break ;
            case "left" :
                if(spriteNumber == 1){
                    image = left1 ;
                }
                if (spriteNumber == 2){
                    image = left2 ;
                }
                break ;
            case "right" :
                if (spriteNumber == 1){
                    image = right1 ;
                }
                if (spriteNumber == 2){
                    image = right2 ;
                }
                break ;
        }
        g2.drawImage(image,screenX, screenY,gamePanel.tileSize,gamePanel.tileSize,null);
    }

    public void pickUpObject(int index){
        //IF THE INDEX IS NOT 999 THEN THE PLAYER HAS TOUCHED AN OBJECT
        if(index != 999 ){
            String objName = gamePanel.obj[index].name ;
            switch (objName) {
                case "Key" :
                    gamePanel.playSoundEffect(1);
                    nbKeys ++ ;
                    gamePanel.obj[index] = null ; 
                    gamePanel.ui.showMessage("You got a key !");
                    break;
                case "Door" :
                    if(nbKeys > 0 ){
                        gamePanel.playSoundEffect(3);
                        gamePanel.obj[index] = null ;
                        nbKeys -= 1 ;
                        gamePanel.ui.showMessage("You opened a door !");
                    }else{
                        gamePanel.ui.showMessage("You need a key to open the door !");
                    }
                    break;
                case "Boots" :
                    gamePanel.playSoundEffect(2);
                    speed += 2 ;
                    gamePanel.obj[index] = null ;
                    gamePanel.ui.showMessage("Speed up !");
                    break ;
                case "Chest" :
                    gamePanel.ui.gameFinished = true ;
                    gamePanel.stopMusic() ;
                    gamePanel.playSoundEffect(4) ;
                    break ;
                default:
                    break;
            }
        }
    }



}
