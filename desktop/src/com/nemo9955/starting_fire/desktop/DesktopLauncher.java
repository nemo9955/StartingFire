package com.nemo9955.starting_fire.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.nemo9955.starting_fire.StartingFire;
import com.nemo9955.starting_fire.storage.Vars;

public class DesktopLauncher {

	public static void main( String[] arg ) {

		if ( Vars.remakePacks ) {

			Settings param = new Settings();
			param.duplicatePadding = true;
			param.pot = false;
			param.flattenPaths = true;
			param.limitMemory = false;
			param.edgePadding = true;

			TexturePacker.process(param, "/home/nemo9955/CodeStation/SF materials/hexas",
						"/home/nemo9955/CodeStation/proiecteJava/StartingFire/android/assets/img", "hexaset");

			TexturePacker.process(param, "/home/nemo9955/CodeStation/SF materials/menu",
						"/home/nemo9955/CodeStation/proiecteJava/StartingFire/android/assets/img", "skinset");
		}
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new StartingFire(), config);
	}
}
