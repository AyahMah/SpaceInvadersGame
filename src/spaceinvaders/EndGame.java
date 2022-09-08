package spaceinvaders;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Displayed when game ends and declares outcome of game (win or lose)
 * Shows final score if player wins
 * Allows player to play again or quit game
 * 
 * @author Ayah M
 * July 2022
 */
public class EndGame {
    private Button playAgainBttn = new Button("Play Again");
    private Button quitBttn = new Button("Quit Game");
    private Label titleLbl = new Label("Space\nInvaders");
    private Label outcomeLbl = new Label("You lose!");
    private Label scoreLbl = new Label();
    private int playerScore;
    
    private Pane pane = new Pane(playAgainBttn, quitBttn, titleLbl, outcomeLbl, scoreLbl);
    private static Scene scene;
    
    public EndGame(boolean won, int score) {
        // Gets player's score
        playerScore = score;
        
        setPane(won);
        scene = new Scene(pane, 500, 700);
        scene.getStylesheets().add("/css/EndStyleSheet.css");
        
        // Set actions to buttons
        playAgainBttn.setOnAction(e -> {
            Game game = new Game();                 
            Main.stage.setScene(game.getGameScene());
        });
        
        quitBttn.setOnAction(e -> {
            Platform.exit();
        });
    }
    
    public void getEndScene(Stage stage) {
        stage.setScene(scene);
    }
    
    // Class creates pane depending on outcome of game
    private void setPane(boolean won) {
        titleLbl.layoutXProperty().bind(pane.widthProperty().subtract(titleLbl.widthProperty()).divide(2));
        titleLbl.setTranslateY(70);
        titleLbl.setId("gameTitle");
    
        outcomeLbl.layoutXProperty().bind(pane.widthProperty().subtract(outcomeLbl.widthProperty()).divide(2));
        outcomeLbl.setTranslateY(350);
        outcomeLbl.setId("outcome");
        scoreLbl.setId("score");
        
        if (won) {
            outcomeLbl.setText("You win!");
            outcomeLbl.setTranslateY(320);
            
            scoreLbl.setText("Your Score: " + playerScore);
            scoreLbl.layoutXProperty().bind(pane.widthProperty().subtract(scoreLbl.widthProperty()).divide(2));
            scoreLbl.setTranslateY(420);
            
            pane.setStyle("-fx-background-image: url('/assets/WinScreen.jpg');");
            outcomeLbl.setStyle("-fx-text-fill: #1fb41b;");
        }
        
        playAgainBttn.setTranslateX(60);
        playAgainBttn.setTranslateY(600);
        playAgainBttn.setFocusTraversable(false);
        
        quitBttn.setTranslateX(290);
        quitBttn.setTranslateY(600);
        quitBttn.setFocusTraversable(false);
    }
}