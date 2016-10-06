package com.nemo9955.starting_fire.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class PackResourcesSF {

	public static void main( String[] args ) {

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

}
