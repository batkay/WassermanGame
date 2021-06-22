package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Inputs implements KeyListener{
    //array of booleans with all the keys
    boolean[] keys= new boolean[524];
    
    //find what keys are pressed
    @Override
    public void keyPressed(KeyEvent e){
    	if(e.getKeyCode()<keys.length) {
            keys[e.getKeyCode()]=true;

    	}
    }
    @Override
    public void keyReleased(KeyEvent e){
    	if(e.getKeyCode()<keys.length) {
            keys[e.getKeyCode()]=false;
    	}

    }
    @Override
    public void keyTyped(KeyEvent e){
        
    }
    
}
    