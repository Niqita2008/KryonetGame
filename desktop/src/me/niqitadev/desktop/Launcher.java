package me.niqitadev.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import me.niqitadev.core.Starter;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class Launcher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration conf = new Lwjgl3ApplicationConfiguration();
		conf.useVsync(true);
		conf.setWindowedMode(800, 600);
		conf.setTitle("KryonetGame");
		new Lwjgl3Application(new Starter(), conf);
	}
}
