package com.nemo9955.starting_fire.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.nemo9955.starting_fire.storage.Assets;
import com.nemo9955.starting_fire.storage.SF;

public class SplashScreen extends ScreenAdapter {

	@Override
	public void show() {

		for (Assets aset : Assets.values()) {
			try {
				aset.loadAsset();
			}
			catch ( Exception e ) {
				Gdx.app.error("assets", "problem loading asset :" + aset.assetPath(), e);
			}
		}
	}

	@Override
	public void render( float delta ) {

		// Gdx.app.debug("assets", "loaded : " + SF.manager.getProgress());
		if ( SF.manager.update() )
			SF.game.setScreen(SF.mc);
	}

	@Override
	public void hide() {

		// Gdx.app.log("life", "leave splash screen");

		SF.atlas = Assets.HEXAS.asset(TextureAtlas.class);
		SF.skin = Assets.SKIN_JSON.asset(Skin.class);
		SF.mainMenu = new MainMenu();
		SF.gameplay = new Gameplay();

	}

}
