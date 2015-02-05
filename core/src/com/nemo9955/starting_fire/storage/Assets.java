package com.nemo9955.starting_fire.storage;

import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader.FreeTypeFontLoaderParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public enum Assets {

	HEXAS("img/hexaset.atlas", TextureAtlas.class), //
	ELEMENTS_PACK("img/skinset.atlas", TextureAtlas.class), //
	D_OLD_MODERN1("fonts/D_OLD_MODERN.ttf", FreeTypeFontGenerator.class, 20), //
	// ARIAL("fonts/arial_32.fnt", BitmapFont.class),//
	SKIN_JSON("img/skinset.json", Skin.class); //

	final String pth;
	final Class<?> cls;
	int size;

	private Assets(String pth, final Class<?> cls) {
		this.pth = pth;
		this.cls = cls;
	}

	private Assets(String pth, final Class<?> cls, int size) {
		this.pth = pth;
		this.cls = cls;
		this.size = size;
	}

	public void loadAsset() throws Exception {
		if ( cls == Texture.class ) {
			TextureParameter param = new TextureParameter();
			param.minFilter = TextureFilter.Linear;
			param.magFilter = TextureFilter.Linear;
			SF.manager.load(pth, Texture.class, param);
		}else if(cls == FreeTypeFontGenerator.class){
			FreeTypeFontLoaderParameter sizeParams = new FreeTypeFontLoaderParameter();
			sizeParams.fontFileName = pth;
			sizeParams.fontParameters.size = size;
			SF.manager.load(this.name()+".ttf", BitmapFont.class, sizeParams);
		}
		else {
			SF.manager.load(pth, cls);
		}
	}

	public <T> T asset(Class<T> type) {
		return SF.manager.get(pth, type);
	}

	public String assetPath() {
		return pth;
	}

	public Class<?> assetClass() {
		return cls;
	}

}
