package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Labyrinth;
import com.badlogic.gdx.Game;

public class DesktopLauncher {
	public static int Width = 1200;
	public static int Height = 1000;
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Labyrinth";
      	config.width = Width;
      	config.height = Height;
		new LwjglApplication(new Labyrinth(), config);
	}
}
