import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

    public final int originalTileSize = 16 ;    //size of charaters and tiles : 16 X 16
    public final int scale = 3 ;  //  each charater 48 X 48 pixels 
    public final int tileSize = originalTileSize * scale ;  
    public final int maxScreenCol = 16 ;
    public final int maxScreenRow = 12 ;  //a grid of 16 X 12 tiles : the ratio is 4/3 
    public final int screenWidth = tileSize * maxScreenCol ;   //48X16 pixels
    public final int screenHeight = tileSize * maxScreenRow ;    //48X12 pixles
    

    //FPR 
    int FPS = 60 ;


    int playerX = 100 ;
    int playerY = 100 ;
    int playerSpeed = 4 ;


    Thread gameThread ;
    KeyHandler keyHandler = new KeyHandler();

    public GamePanel ()
    {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight)) ;
        this.setBackground(Color.BLACK);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS ;
        double nextDrawTime = System.nanoTime() + drawInterval ;

        while (gameThread != null)
        {
            update();
            repaint();
            try {
                double remaintingTime = nextDrawTime - System.nanoTime() ;
                remaintingTime = remaintingTime / 1000000 ;
                if (remaintingTime < 0)
                {
                    remaintingTime = 0 ;
                }
                Thread.sleep((long)remaintingTime);
                nextDrawTime += drawInterval ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update ()
    {
        if (keyHandler.upPressed)
        {
            playerY -= playerSpeed ;
        }
        if (keyHandler.downPressed)
        {
            playerY += playerSpeed ;
        }
        if (keyHandler.leftPressed)
        {
            playerX -= playerSpeed ;
        }
        if(keyHandler.rightPressed)
        {
            playerX += playerSpeed ;
        }
    }
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g ;
        g2.setColor(Color.WHITE);
        g2.fillRect(playerX, playerY, tileSize , tileSize);
        g2.dispose();
    }

}