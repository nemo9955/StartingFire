package com.nemo9955.starting_fire.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.nemo9955.starting_fire.storage.Assets;
import com.nemo9955.starting_fire.storage.SF;

public class SplashScreen extends ScreenAdapter {

	@Override
	public void show() {
		
		FileHandleResolver resolver = new InternalFileHandleResolver();
		SF.manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		SF.manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
		
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
		SF.skin = new Skin();
		for (BitmapFont aset : SF.manager.getAll(BitmapFont.class, new Array<BitmapFont>() )) 
			SF.skin.add(aset.getData().getFontFile().nameWithoutExtension(), aset, BitmapFont.class);
		SF.skin.load(Gdx.files.internal("img/.json"));
		
//		SF.skin = Assets.SKIN_JSON.asset(Skin.class);
		SF.mainMenu = new MainMenu();
		SF.gameplay = new Gameplay();

	}

}
