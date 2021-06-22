package game;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class musiccode{
void playMusic(String musicLocation){
  try{
    File musicPath = new File(musicLocation);
    if(musicPath.exists()){
      AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
      Clip clip = AudioSystem.getClip();
      clip.open(audioInput);
      clip.start();
      clip.loop(Clip.LOOP_CONTINUOUSLY);
      try {
            Thread.sleep(200000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
