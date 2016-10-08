package com.nemo9955.starting_fire.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class PackResourcesSF {

	public static void main(String[] args) {

		Settings param = new Settings();
		param.duplicatePadding = true;
		param.pot = false;
		param.flattenPaths = true;
		param.limitMemory = false;
		param.edgePadding = true;

		// TexturePacker.process(param, "../RAWassets/hexas",
		// "../core/assets/img", "hexaset");
		// TexturePacker.process(param, "../RAWassets/menu",
		// "../core/assets/img", "skinset");

		TexturePacker.process(param, "../RAWassets", "../core/assets/img", "sprites");
	}

}
