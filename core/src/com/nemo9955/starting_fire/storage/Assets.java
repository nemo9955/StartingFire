package com.nemo9955.starting_fire.storage;

import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public enum Assets {

	HEXAS("img/hexaset.atlas", TextureAtlas.class), //
	SKIN_ATLAS("img/skinset.atlas", TextureAtlas.class), //
	D_OLD_MODERN("fonts/D_OLD_MODERN.ttf", FreeTypeFontGenerator.class);//
	// ARIAL("fonts/arial_32.fnt", BitmapFont.class),//
	// SKIN_JSON("img/skinset.json", Skin.class); //

	final String pth;
	final Class<?> cls;

	private Assets(String pth, final Class<?> cls) {
		this.pth = pth;
		this.cls = cls;
	}

	public void loadAsset() throws Exception {
		if (cls == Texture.class) {
			TextureParameter param = new TextureParameter();
			param.minFilter = TextureFilter.Linear;
			param.magFilter = TextureFilter.Linear;
			SF.manager.load(pth, Texture.class, param);
		} else {
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
