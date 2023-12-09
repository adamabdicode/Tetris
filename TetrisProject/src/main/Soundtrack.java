package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineEvent.Type;

public class Soundtrack {
	Clip musicClip;
	URL url[] = new URL[10];

	public Soundtrack() {
		url[0] = getClass().getResource("/Tetris.wav");
		url[1] = getClass().getResource("/delete line.wav");
		url[2] = getClass().getResource("/gameover.wav");
		url[3] = getClass().getResource("/rotation.wav");
		url[4] = getClass().getResource("/touch floor.wav");
	}

	/**
	 * Plays the soundtrack
	 */
	public void play(int i, boolean music) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(url[i]);
			Clip clip = AudioSystem.getClip();

			if (music) {
				musicClip = clip;
			}

			clip.open(ais);
			clip.addLineListener(new LineListener() {

				@Override
				public void update(LineEvent event) {
					// TODO Auto-generated method stub
					if (event.getType() == Type.STOP) {
						clip.close();
					}
				}
			});
			ais.close();
			clip.start();

		} catch (Exception e) {

		}
	}
	public void loop() {
		musicClip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		musicClip.stop();
		musicClip.close();
	}
}
