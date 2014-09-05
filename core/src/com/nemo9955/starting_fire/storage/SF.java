package com.nemo9955.starting_fire.storage;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.nemo9955.starting_fire.StartingFire;
import com.nemo9955.starting_fire.states.Gameplay;
import com.nemo9955.starting_fire.states.MainMenu;
import com.nemo9955.starting_fire.states.MenuController;

public class SF {

	public static final AssetManager	manager			= new AssetManager();
	public static final SpriteBatch		spritesBatch	= new SpriteBatch();
	public static StartingFire			game;

	public static Skin					skin;
	public static TextureAtlas			atlas;

	public static MenuController		mc				= new MenuController();
	public static MainMenu				mainMenu;
	public static Gameplay				gameplay;

}
