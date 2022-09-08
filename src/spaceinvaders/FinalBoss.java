package spaceinvaders;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author vivid
 */
public class FinalBoss extends Game {
    private Sprite finalBoss = new Sprite(222, 640, "player", new Image("/assets/FinalBossSprite.png", SCREEN_WIDTH-200, SCREEN_HEIGHT-200, true, true));
    private boolean stopAnimation = false;
    
    private Pane pane = new Pane(finalBoss, player);
    private Scene scene = new Scene(pane);
    
    private final Random random = new Random();
    private final int bossWidth = 300;
    private final int bossHeight = 150;
    
    public FinalBoss(int score) {
        //animateFinalBoss();
    }
    /*
    private void animateFinalBoss() {
        moveFinalBoss();
        Timeline timeline = new Timeline();
        //timelineList.add(timeline);
        KeyFrame key1 = new KeyFrame(Duration.millis(random.nextInt(2000) + 1000),
                        e -> enemyFireAnimation(timeline, finalBoss));
        timeline.getKeyFrames().add(key1);
        timeline.setCycleCount(1);
        timeline.setOnFinished(e -> {
                if (finalBoss.getAnimationStop()) {
                        timeline.stop();
                } else {
                        timeline.play();
                }
        });
        timeline.play();
    }*/
    
    private void moveFinalBoss() {
        Timeline timeline = new Timeline();

        KeyFrame end = getBossMovementKeyFrame(finalBoss);

        timeline.getKeyFrames().add(end);
        timeline.setCycleCount(1);
        timeline.setOnFinished(e -> {
            checkYBounds(finalBoss);
            if (getAnimationStop()) {
                timeline.stop();
            } else {
                timeline.getKeyFrames().remove(0);
                timeline.getKeyFrames().add(getBossMovementKeyFrame(finalBoss));
                timeline.playFromStart();
            }
        });
        
        if (getAnimationStop()) {
            timeline.playFromStart();
        }
    }
    
    private KeyFrame getBossMovementKeyFrame(Sprite finalBoss) {
        KeyFrame kf = new KeyFrame(Duration.millis(random.nextInt(2300) + 1000),
            new KeyValue(finalBoss.xProperty(),
                random.nextInt(SCREEN_WIDTH - (int) bossWidth)),
            new KeyValue(finalBoss.yProperty(),
                (int) finalBoss.getY() + random.nextInt(100) + 40));
        return kf;
    }
    
    private void checkYBounds(Sprite finalBoss) {
        if (finalBoss.getY() >= (SCREEN_HEIGHT*0.6)) {
            
        }
    }
    
    public boolean getAnimationStop() {
		return stopAnimation;
	}
	
	public void setAnimationStop(boolean stop) {
		this.stopAnimation = stop;
	}
}
