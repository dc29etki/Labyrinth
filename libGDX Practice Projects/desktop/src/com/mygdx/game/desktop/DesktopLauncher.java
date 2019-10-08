package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Labyrinth;
import com.badlogic.gdx.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "SpriteTest";
      	config.width = 800;
      	config.height = 800;
		new LwjglApplication(new Labyrinth(), config);
	}
}
