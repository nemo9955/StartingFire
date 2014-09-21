package com.nemo9955.starting_fire.game.tiles;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.nemo9955.starting_fire.game.ashley.components.CInfo;
import com.nemo9955.starting_fire.game.ashley.components.CInfo.Spot;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.storage.SF;

public class DecalFactory {

	public static Array<AtlasRegion>	weeds	= SF.atlas.findRegions("weeds");
	public static Array<AtlasRegion>	stones	= SF.atlas.findRegions("stones");

	public static void useRandomElement( Entity entity ) {
		CInfo in = CM.Info.get(entity);
		if ( in.spot != Spot.empty )
			return;

		// EntityManager manager = CM.Info.get(entity).world.manager;

		CTexture te = CM.Tex.get(entity);
		if ( in.hex == HexBase.Grass && MathUtils.randomBoolean(0.4f) )
			te.tex.insert(1, weeds.random());
		else if ( in.hex == HexBase.Dirt && MathUtils.randomBoolean(0.15f) )
			te.tex.insert(1, stones.random());

	}

}
