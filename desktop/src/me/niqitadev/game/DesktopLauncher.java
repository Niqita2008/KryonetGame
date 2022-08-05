package me.niqitadev.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import me.niqitadev.game.Starter;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration conf = new Lwjgl3ApplicationConfiguration();
		conf.setForegroundFPS(60);
		conf.setWindowedMode(800, 600);
		conf.setTitle("KryonetGame");
		new Lwjgl3Application(new Starter(), conf);
	}
}
