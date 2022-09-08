package spaceinvaders;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Creates player, enemy, and bullet sprites
 * 
 * @author Ayah M
 * July 2022
 */
public class Sprite extends ImageView {
    private boolean isDead = false;
    private int velX = 0;
    private int playerScore = 0;
    
    private final int VELOCITY = 5;
    String type;

    Sprite(int x, int y, String type, Image image) {
        super(image);
        this.type = type;
        setTranslateX(x);
        setTranslateY(y);
    }
    
    // Methods used to move sprites
    public void moveLeft() {
        setVelX(-VELOCITY);
    }

    public void moveRight() {
        setVelX(VELOCITY);
    }

    public void moveUp(int x) {
        setTranslateY(getTranslateY() - x);
    }

    public void moveDown(int y) {
        setTranslateY(getTranslateY() + y);
    }
    
    public void setVelX(int velX) {
        this.velX = velX;
    }
    
    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }
    
    public void setPlayerScore(int score) {
        playerScore = score;
    }
    
    public int getVelX() {
        return velX;
    }
    
    public boolean getIsDead() {
        return isDead;
    }
    
    public int getPlayerScore() {
        return playerScore;
    }
}