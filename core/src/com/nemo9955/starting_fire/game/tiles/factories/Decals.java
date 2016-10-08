package com.nemo9955.starting_fire.game.tiles.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.nemo9955.starting_fire.storage.SF;

public class Decals {

	public static Array<AtlasRegion>	weeds	= SF.atlas.findRegions("weeds");
	public static Array<AtlasRegion>	stones	= SF.atlas.findRegions("stones");

	public static void useRandomElement( Entity entity ) {
//		CWorld in = CMap.world.get(entity);
//		if ( in.spot != Spot.empty )
//			return;
//
//		// EntityManager manager = CMap.world.get(entity).world.manager;
//
//		CTexture te = CMap.texture.get(entity);
//		if ( in.hex == TileTypes.Grass && MathUtils.randomBoolean(0.4f) )
//			te.insert(1, weeds.random());
//		else if ( in.hex == TileTypes.Dirt && MathUtils.randomBoolean(0.15f) )
//			te.insert(1, stones.random());

	}

}
