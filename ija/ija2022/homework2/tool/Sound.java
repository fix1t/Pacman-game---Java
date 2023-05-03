package ija.ija2022.homework2.tool;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

/**
 * Class for processing sound files and manipulation with them.
 * @author Jakub Mikyšek
 */
public class Sound {
  Clip clip;

  /**
   * Stores all the paths to sound files in array.
   */
  URL[] soundURL = new URL[10];

  /**
   * Fill {@code soundURL} array with sound file paths.
   */
  public Sound() {
    soundURL[0] = getClass().getResource("./lib/PacmanSoundtrack.wav");
  }

  /**
   * Access sound files and opens them.
   *
   * @param i index to access sound files in soundURL array
   */
  public void setFile(int i) {
    try {
      AudioInputStream music = AudioSystem.getAudioInputStream(soundURL[i]);
      clip = AudioSystem.getClip();
      clip.open(music);
    }
    catch (Exception ignored) {
    }
  }

  /**
   * Start audio playback.
   */
  public void play() {
    clip.start();
  }

  /**
   * Continuously audio loop playback.
   */
  public void loop() {
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  /**
   * Stop audio playback.
   */
  public void stop() {
    clip.stop();
  }

}
