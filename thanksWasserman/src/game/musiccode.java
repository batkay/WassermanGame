package game;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JOptionPane;

public class musiccode{
void playMusic(String musicLocation){
  try{
    File musicPath = new File(musicLocation);
    if(musicPath.exists()){
      AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
      Clip clip = AudioSystem.getClip();
      clip.open(audioInput);

      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

      gainControl.setValue(-40.0f); // Reduce volume by 10 decibels.      clip.open(audioInput);
      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      /*try {
            Thread.sleep(200000);
        }
      catch (InterruptedException e) {
            e.printStackTrace();
        }*/
      JOptionPane.showMessageDialog(null, "test");
    }
    else{
      System.out.println("bruh");
    }

  }
  catch(Exception e){
    e.printStackTrace();
  }
}
}
