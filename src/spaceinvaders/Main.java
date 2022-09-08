package spaceinvaders;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Ayah Mahmoud
 * July 2022
 */
public class Main extends Application {    
    public static Stage stage;
    public static StartGame start = new StartGame();

    @Override
    public void start(Stage stage) {       
        this.stage = stage;
        start.getStartScene(stage);

        stage.setTitle("Space Invaders");
        stage.setResizable(false);      
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}