package com.nemo9955.starting_fire.storage;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public enum Fonts {
	
	D_OLD_MODERN_D(Assets.D_OLD_MODERN ,32);
	
	int size ;
	Assets aset ;

	private Fonts(Assets aset , int size){
		this.size=size;
		this.aset=aset ;
	}
	
//	 else if (cls == FreeTypeFontGenerator.class) {
//			//SF.manager.load(pth, cls);
//			FreeTypeFontLoaderParameter sizeParams = new FreeTypeFontLoaderParameter();
//			sizeParams.fontFileName = pth;
//			sizeParams.fontParameters.size = size;
//			SF.manager.load( this.name()+".ttf", BitmapFont.class, sizeParams);
//		}
	
	
	public static void loadFonts(){

		for (Fonts fnt :Fonts.values()) {
			FreeTypeFontGenerator gen = SF.manager.get(fnt.aset.assetPath(), FreeTypeFontGenerator.class);
			FreeTypeFontParameter parameter = new FreeTypeFontParameter();
			parameter.size = fnt.size;
			BitmapFont font = gen.generateFont(parameter);
			 
			
			SF.skin.add(fnt.name() , font, BitmapFont.class);
//			System.out.println(aset.getData());
			// SF.skin.add(aset.getData().getFontFile().nameWithoutExtension(),
			// aset, BitmapFont.class);
		}
	}
	
}
