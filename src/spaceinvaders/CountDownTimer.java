package spaceinvaders;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.Timer;

/**
 * Creates timer that counts down and then displays end game screen
 * Used in the Game class
 * 
 * @author Ayah M
 * July 2022
 */
public class CountDownTimer {
    private Timer timer;
    private int second, minute;
    private String ddSecond, ddMinute;
    private DecimalFormat dFormat = new DecimalFormat("00");
    private String timerStr;
    private boolean isOver = false;
    
    public CountDownTimer(int mins, int secs) {
        // Sets time on timer based on paramters passed in Game class
        minute = mins;
        second = secs;
        
        formatTimer();      
        countdownTimer();
        timer.start();
    }
    
    public void countdownTimer() {
        timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                second--;            
                
                if(second == -1) {
                    second = 59;
                    minute--;
                }
                
                formatTimer();
                
                // Ends timer when timer reaches 0
                if(minute == 0 && second == 0) {
                    timer.stop();
                    isOver = true;
                }    
            }
        });       
    }
    
    // Formats how time is displayed
    public void formatTimer() {
        ddSecond = dFormat.format(second);
        ddMinute = dFormat.format(minute);
        timerStr = ddMinute + ":" + ddSecond;
    }
    
    public String getTimer() {
        return timerStr;
    }
    
    public boolean getIsOver() {
        return isOver;
    }
}
