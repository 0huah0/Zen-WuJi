package me.shizh.ai.zen.features.voice;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class Mp3Voice {
	/**
	 * mp3 player
	 * @param mp3 filename
	 */
	public void play(String filename) {
		try {
			BufferedInputStream buffer = new BufferedInputStream(
					new FileInputStream(filename));
			new Player(buffer).play();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}