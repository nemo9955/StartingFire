package com.nemo9955.starting_fire.game.tiles.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.nemo9955.starting_fire.game.ashley.EntityManager;
import com.nemo9955.starting_fire.game.ashley.components.CCoordinate;
import com.nemo9955.starting_fire.game.ashley.components.CInfo;
import com.nemo9955.starting_fire.game.ashley.components.CInfo.Spot;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.world.World;
import com.nemo9955.starting_fire.storage.SF;

public class Trees {

	public static Array<AtlasRegion>	trees	= SF.atlas.findRegions("trees_beech");

	public static void useElement( Entity entity ) {
		CInfo in = CM.Info.get(entity);
		if ( in.spot == Spot.used )
			return;
		else
			in.spot = Spot.used;

		World world = CM.Info.get(entity).world;
		EntityManager manager = world.manager;
		manager.addTexture(entity, trees.random());

		CCoordinate co = CM.Coor.get(entity);

		Entity ent = world.getHex(co.q, co.r - 1);
		CInfo ine = CM.Info.get(ent);
		if ( ine.spot == Spot.empty )
			ine.spot = Spot.coverd;

		// CM.Tex.get(ent).tex.add(SF.atlas.findRegion("sand"));
		// manager.addTexture(ent, SF.atlas.findRegion("sand"));

		// CCoordinate co = CM.Coor.get(manager.getEntity());
		// CInfo in = CM.Info.get(manager.getEntity());

	}

}
