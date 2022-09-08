package spaceinvaders;

import java.util.List;
import java.util.stream.Collectors;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Class that runs game animations
 * Player uses arrows to move player sprite and spacebar to shoot
 * Timer is set to 1 min but can be changed
 * Player has 3 lives
 * 
 * @author Ayah M
 * July 2022
 */
public class Game {
    public final int SCREEN_WIDTH = 500;
    public final int SCREEN_HEIGHT = 700;
    public final int SPRITE_WIDTH = 50;
    public final int SPRITE_HEIGHT = 50;
    private static final int GAME_MINS = 1, GAME_SECS = 0;  // Change timer here
    private final int ENEMY_VELY = 1;
    private final int PLAYER_BULLET_SPEED = 5;
    private final int ENEMY_BULLET_SPEED = 4;
    
    private int counter = 0, spawnTime = 100;
    private double t = 0;
    public CountDownTimer timer;
    private int numEnemies = 5;
    private int playerScore = 0;
    private int playerHP = 3;   // Change starting HP here
    public boolean isGameOver = false;
    private AnimationTimer animation;
    
    private final Label scoreLbl = new Label("Score: " + playerScore);
    private Label timerLbl = new Label();
    private final Pane scorePane = new Pane(scoreLbl, timerLbl);
    private final Pane gamePane = new Pane();
    private final HBox healthPane = new HBox();
    private final StackPane pane = new StackPane(scorePane, gamePane, healthPane);
    private Scene scene;
    
    private Image playerImage = new Image("/assets/PlayerSprite.png", SPRITE_WIDTH, SPRITE_HEIGHT, true, true);
    protected final Sprite player = new Sprite(SCREEN_WIDTH/2 - SPRITE_WIDTH/2, SCREEN_HEIGHT-60, "player", 
            playerImage);
    double x = player.getTranslateX();
    
    public Game() {     
        timer = new CountDownTimer(GAME_MINS, GAME_SECS);
        Main.stage.setScene(createContent());
    }

    public Scene createContent() {
        timerLbl.setMaxWidth(Double.MAX_VALUE);
        timerLbl.layoutXProperty().bind(pane.widthProperty().subtract(timerLbl.widthProperty()).divide(2));
        
        // Displays player HP in top right of screen
        ImageView playerView; 
        for (int i = playerHP; i > 0; i--) {
            playerView = new ImageView(playerImage);
            playerView.setFitWidth(30);
            playerView.setPreserveRatio(true);
            healthPane.getChildren().add(playerView);
        }
        healthPane.setAlignment(Pos.TOP_RIGHT);
        
        pane.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        gamePane.getChildren().add(player);
        
        scene = new Scene(pane);
        scene.getStylesheets().add("/css/GameStyleSheet.css");
        
        animation = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {             
                update();
                
                x += player.getVelX();
                player.setTranslateX(x);
                
                // Set bounds for player movement
                if (x <= 0)
                    player.setTranslateX(0);
                
                if (x >= SCREEN_WIDTH - SPRITE_WIDTH)
                    player.setTranslateX(SCREEN_WIDTH - SPRITE_WIDTH);
                
                counter++;
                spawnEnemy();
                
                scoreLbl.setText("Score: " + playerScore);
                timerLbl.setText("Time: " + timer.getTimer());
            }     
        };
        
        animation.start();
        playerControls();
        
        return scene;
    }  
    
    private void update(){
        t += 0.014;
        
        // Checks if bullets, enemies or player collides and declares sprites dead
        sprites().forEach(s -> {
            switch(s.type) {
                case "playerBullet":
                    s.moveUp(PLAYER_BULLET_SPEED);
                    sprites().stream().filter(e -> e.type.equals("enemy")).forEach(enemy -> {
                        if (s.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            enemy.setIsDead(true);
                            s.setIsDead(true);
                            playerScore += 50;
                            player.setPlayerScore(playerScore);
                            numEnemies--;
                        }
                    });
                    break;
                case "enemyBullet":
                    s.moveDown(ENEMY_BULLET_SPEED);
                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        playerHP--;
                        healthPane.getChildren().remove(0);
                        s.setIsDead(true);
                    }
                    break;
                case "enemy":
                    s.moveDown(ENEMY_VELY);
                    if (s.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        playerHP--;
                        healthPane.getChildren().remove(0);
                        s.setIsDead(true);
                    }
                    if (t > 2) {
                        // Enemies set to 30% chance to shoot every 2 seconds
                        if(Math.random() < 0.3) {
                            shoot(s);
                        }
                    }
                    break;
            }
        });
        
        // Removes dead sprites from pane
        gamePane.getChildren().removeIf(n -> {
           Sprite s = (Sprite) n;
           return s.getIsDead();
        });
        
        if (t > 2) {
            // Declares player dead if HP reaches 0
            if (playerHP == 0) 
                player.setIsDead(true);            
            
            // If player is not dead, animations continue
            if (!player.getIsDead())
                t = 0;
            else
                isGameOver = true;       
            
            // Checks if timer is over
            if (timer.getIsOver())
                isGameOver = true;
        }
        
        // Stops animation id game is over
        if (isGameOver) {
            animation.stop();                    
            EndGame end = new EndGame(!player.getIsDead(), playerScore);
            end.getEndScene(Main.stage);
        }
    }
    
    // Sets player controls when keys are pressed
    public void playerControls() {
        scene.setOnKeyPressed(e -> {
            switch(e.getCode()) {
                case A:
                case LEFT:
                    player.moveLeft();
                    break;
                case D:
                case RIGHT:
                    player.moveRight();
                    break;
                case SPACE:
                    shoot(player);
                    break;
           } 
        });
        
        // Sets player velocity to 0 when keys are released
        scene.setOnKeyReleased(e -> {
            switch(e.getCode()) {
                case A:
                case LEFT:
                case D:
                case RIGHT:
                    player.setVelX(0);
                    break;
            } 
        });
    }
    
    // Creates bullet sprites for player and enemies
    private void shoot(Sprite shooter){
        Image bulletImage = new Image("/assets/EnemyBullet.png", 30, 50, true, true);
        if (shooter.type == "player") {
            bulletImage = new Image("/assets/PlayerBullet.png", 30, 50, true, true);
        } 
        
        Sprite s = new Sprite((int) shooter.getTranslateX()+10, (int) shooter.getTranslateY(), shooter.type+"Bullet", bulletImage);
        gamePane.getChildren().add(s);
    }
    
    // Enemies randomly appear anywhere at top of scene
    private void spawnEnemy() {
        double spawnPosition = Math.random();
        int ex = (int) ((SCREEN_WIDTH - SPRITE_WIDTH) * spawnPosition);

        if (counter % spawnTime == 0) {
            Sprite enemy = new Sprite(ex, 0, "enemy", new Image("/assets/EnemySprite.png", SPRITE_WIDTH, SPRITE_HEIGHT, true, true));
            sprites().add(enemy);
            gamePane.getChildren().add(enemy);
        }
    }    
    
    private List<Sprite> sprites() {
        return gamePane.getChildren().stream().map(n -> (Sprite)n).collect(Collectors.toList());
    }
    
    public void stopAnimation() {
        animation.stop();
    }
    
    public Scene getGameScene() {
        return scene;
    }
}