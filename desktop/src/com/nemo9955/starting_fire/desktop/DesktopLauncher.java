package com.nemo9955.starting_fire.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.nemo9955.starting_fire.StartingFire;
import com.nemo9955.starting_fire.storage.Vars;

public class DesktopLauncher {

	public static void main( String[] arg ) {

		if ( Vars.remakeHexas ) {

			Settings param = new Settings();
			param.duplicatePadding = true;
			param.pot = false;
			param.flattenPaths = true;
			param.limitMemory = false;
			param.edgePadding = true;

			TexturePacker.process(param, "/home/nemo9955/CodeStation/proiecteJava/starting-fire/android/assets/hexas",//
					"/home/nemo9955/CodeStation/proiecteJava/starting-fire/android/assets/img", "hexaset");
		}
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new StartingFire(), config);
	}
}
