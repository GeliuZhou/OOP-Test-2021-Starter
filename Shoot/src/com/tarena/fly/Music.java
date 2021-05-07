package com.tarena.fly;

import java.applet.Applet;
import java.applet.AudioClip; 
import java.io.File;
import java.net.URI;
import java.net.URL;


import javax.swing.JFrame;
 
public class Music extends JFrame{ 
    
	private static final long serialVersionUID = 1L;
	File f;
    URI uri;
    URL url;
 
    Music(){
        try{
          f = new File("music.mp3"); 
          uri = f.toURI();
          url = uri.toURL(); //Resolve address
          AudioClip aau; 
          aau = Applet.newAudioClip(url);
          aau.loop();  //Loop
        }
        catch (Exception e)
        {
            e.printStackTrace();
        } 
    }
    public static void main(String args[]) { 
        new Music();
    }
}