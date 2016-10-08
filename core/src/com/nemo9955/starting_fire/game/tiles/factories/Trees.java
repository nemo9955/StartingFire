package com.nemo9955.starting_fire.game.tiles.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.nemo9955.starting_fire.game.ashley.components.CCoordinate;
import com.nemo9955.starting_fire.game.ashley.components.CMap;
import com.nemo9955.starting_fire.game.ashley.components.CWorld;
import com.nemo9955.starting_fire.game.world.World;
import com.nemo9955.starting_fire.storage.SF;

public class Trees {

	public static Array<AtlasRegion> trees = SF.atlas.findRegions("trees_beech");

	public static void useElement(Entity entity) {
		CWorld in = CMap.world.get(entity);
//		if (in.spot == Spot.used)
//			return;
//		else
//			in.spot = Spot.used;

		World world = CMap.world.get(entity).world;
		world.addTexture(entity, trees.random());

		CCoordinate co = CMap.coordinate.get(entity);

		Entity ent = world.getHex(co.q, co.r - 1);
		CWorld ine = CMap.world.get(ent);
//		if (ine.spot == Spot.empty)
//			ine.spot = Spot.coverd;

		// CMap.texture.get(ent).tex.add(SF.atlas.findRegion("sand"));
		// manager.addTexture(ent, SF.atlas.findRegion("sand"));

		// CCoordinate co = CMap.coordinate.get(manager.getEntity());
		// CWorld in = CMap.world.get(manager.getEntity());

	}

}
