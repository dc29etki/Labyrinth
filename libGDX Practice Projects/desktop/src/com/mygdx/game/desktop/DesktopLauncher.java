package com.mygdx.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Labyrinth;
import com.badlogic.gdx.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Labyrinth";
      	config.width = 900;
      	config.height = 900;
      	config.resizable = false;
      	config.addIcon("favicon.png", Files.FileType.Internal);
		new LwjglApplication(new Labyrinth(), config);
	}
}
