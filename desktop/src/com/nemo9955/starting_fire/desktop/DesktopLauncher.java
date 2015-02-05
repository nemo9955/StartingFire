package com.nemo9955.starting_fire.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.nemo9955.starting_fire.StartingFire;
import com.nemo9955.starting_fire.storage.Vars;

public class DesktopLauncher {

	public static void main( String[] arg ) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1250;
		config.height = 800;
		config.backgroundFPS = 30;
		config.foregroundFPS = 60;
		config.vSyncEnabled = false;
		config.title = Vars.title;

		new LwjglApplication(new StartingFire(), config);
	}
}
