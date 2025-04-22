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

    // WORLD SETTINGS :
    public final int maxWorldCol = 50 ;
    public final int maxWorldRow = 50 ;
    public final int worldWidth = tileSize * maxWorldCol ;
    public final int worldHeight = tileSize * maxWorldRow ;
    

    //FPR : frames per second
    int FPS = 60 ;   //Update the game each 60 seconds 



    //SYSTEM
    Thread gameThread ;
    CollisionChecker collisionChecker = new CollisionChecker(this);
    AssetSetter assetSetter = new AssetSetter(this) ;
    KeyHandler keyHandler = new KeyHandler();
    TileManager tileManager = new TileManager(this) ;
    Sound sound = new Sound();


    //ENTITY AND OBJECTS
    Player player = new Player(this, keyHandler) ;
    SuperObject[] obj = new SuperObject[10] ;

    public GamePanel ()
    {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight)) ;
        this.setBackground(Color.BLACK);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setUpGame()
    {
        assetSetter.setObject();
        playMusic(0);
    }

    public void startGameThread()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {   //game loop : update + repaint + sleep

        double drawInterval = 1000000000/FPS ;    //0.016secondes(nanosecondes)
        double nextDrawTime = System.nanoTime() + drawInterval ;

        while (gameThread != null)
        {
            update();
            repaint();
            try {
                double remaintingTime = nextDrawTime - System.nanoTime() ;
                remaintingTime = remaintingTime / 1000000 ;    //en millisecondes
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
        player.update();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g ;

        //TILES
        tileManager.draw(g2);

        //OBJECTS
        for(int i = 0 ; i < obj.length ; i++){
            if(obj[i] != null)
            {
                obj[i].draw(g2, this);
            }
        }

        //PLAYER
        player.draw(g2);
        g2.dispose();
    }

    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void stopMusic(){
        sound.stop();
    }
    public void playSoundEffect(int i){
        sound.setFile(i);
        sound.play();
    }

}