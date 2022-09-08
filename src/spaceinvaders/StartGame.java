package spaceinvaders;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Displays start screen when program is run
 * Allows player to start game or quit program
 * 
 * @author Ayah M
 */
public class StartGame {   
    private Button startBttn = new Button("Start");
    private Button quitBttn = new Button("Quit");
    private Button playAgainBttn = new Button("Play Again");
    private Label titleLbl = new Label("Space\nInvaders");
    private Label controlsLbl = new Label("Controls:\n\nPress:\tA or ← to move left\n"
            + "\t\tD or → to move right\n"
            + "\t\tSPACEBAR to shoot");
    
    private Pane pane = new Pane(startBttn, quitBttn, titleLbl, controlsLbl);
    private static Scene scene;
    
    public StartGame() {
        // Load fonts
        Font.loadFont(getClass().getResourceAsStream("/css/01_Digitall.ttf"), 16);
        Font.loadFont(getClass().getResourceAsStream("/css/NeogreyMedium.otf"), 16);
     
        setPane();
        scene = new Scene(pane, 500, 700);
        scene.getStylesheets().add("/css/StartStyleSheet.css");
        
        // Set actions to buttons
        startBttn.setOnAction(e -> {
            Game game = new Game();
            scene = game.getGameScene();            
            Main.stage.setScene(scene);
        });
        
        quitBttn.setOnAction(e -> {
            Platform.exit();
        });
    }
    
    public static void getStartScene(Stage stage) {
        stage.setScene(scene);
    }
    
    // Creates start screen pane
    private void setPane() {
        titleLbl.setTranslateX(50);
        titleLbl.setTranslateY(70);
        titleLbl.setId("gameTitle");
        
        controlsLbl.setTranslateX(92);
        controlsLbl.setTranslateY(400);
        controlsLbl.setId("controls");
        
        startBttn.setTranslateX(92);
        startBttn.setTranslateY(600);
        
        quitBttn.setTranslateX(310);
        quitBttn.setTranslateY(600);
    }
}