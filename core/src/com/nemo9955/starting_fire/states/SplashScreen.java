package com.nemo9955.starting_fire.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.nemo9955.starting_fire.storage.Assets;
import com.nemo9955.starting_fire.storage.Fonts;
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
			} catch (Exception e) {
				Gdx.app.error("assets", "problem loading asset :" + aset.assetPath(), e);
			}
		}
	}

	@Override
	public void render(float delta) {

		// Gdx.app.debug("assets", "loaded : " + SF.manager.getProgress());
		if (SF.manager.update())
			SF.game.setScreen(SF.mc);
	}

	@Override
	public void hide() {

		// Gdx.app.log("life", "leave splash screen");
		SF.atlas = Assets.HEXAS.asset(TextureAtlas.class);

		SF.skin = new Skin();
		Fonts.loadFonts();
		SF.skin.addRegions(Assets.SKIN_ATLAS.asset(TextureAtlas.class));
		SF.skin.load(Gdx.files.internal("img/skinset.json"));
		
		
		//FIXME added windowStyle programmatically until possible bug is fixed
		
		WindowStyle wsd = new WindowStyle(SF.skin.getFont("D_OLD_MODERN_D"), Color.CYAN, SF.skin.getDrawable("pix30"));
		WindowStyle wse = new WindowStyle(SF.skin.getFont("ARIAL_D"), Color.BLACK, SF.skin.getDrawable("pix100"));

		SF.skin.add("default", wsd, WindowStyle.class);
		SF.skin.add("error", wse, WindowStyle.class);

		SF.mainMenu = new MainMenu();
		SF.gameplay = new Gameplay();

	}
}
