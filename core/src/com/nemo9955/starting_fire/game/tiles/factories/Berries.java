package com.nemo9955.starting_fire.game.tiles.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.nemo9955.starting_fire.game.ashley.EntityManager;
import com.nemo9955.starting_fire.game.ashley.components.CHit;
import com.nemo9955.starting_fire.game.ashley.components.CHit.IHitable;
import com.nemo9955.starting_fire.game.ashley.components.CInfo;
import com.nemo9955.starting_fire.game.ashley.components.CInfo.Spot;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.ashley.components.CTexture;
import com.nemo9955.starting_fire.game.ashley.components.CTimer;
import com.nemo9955.starting_fire.game.ashley.components.CUpdate;
import com.nemo9955.starting_fire.game.ashley.components.CUpdate.IUpdatable;
import com.nemo9955.starting_fire.game.events.Events;
import com.nemo9955.starting_fire.storage.SF;

public class Berries {

	public static Array<AtlasRegion>	bush	= SF.atlas.findRegions("bush");
	public static Array<AtlasRegion>	berry	= SF.atlas.findRegions("berry");

	public static void useElement( Entity entity ) {
		CInfo in = CM.Info.get(entity);
		if ( in.spot != Spot.empty )
			return;
		else
			in.spot = Spot.used;
		EntityManager manager = CM.Info.get(entity).world.manager;

		manager.addTexture(entity, bush.random());
		manager.addTimer(entity, growTime());
		manager.addUpdate(entity, update);
	}

	private static void pickBerries( Entity entity ) {
		CInfo i = CM.Info.get(entity);
		i.world.manager.addTimer(entity, growTime());
		i.world.manager.addUpdate(entity, update);

		CTexture t = CM.Tex.get(entity);
		t.removeAll(berry, true);
		entity.remove(CHit.class);

		Events.Berries.addAmount(pickAmount());
	}

	private static void grownBerries( Entity entity ) {
		EntityManager manager = CM.Info.get(entity).world.manager;
		manager.addTexture(entity, berry.random());
		manager.addHit(entity, hit);

		entity.remove(CUpdate.class);
		entity.remove(CTimer.class);
	}

	private static float growTime() {
		return 1 + MathUtils.random(2);
	}

	private static int pickAmount() {
		return 2 + MathUtils.random(5);
	}

	private static IHitable		hit		= new IHitable() {

											@Override
											public void hit( Entity ent ) {
												pickBerries(ent);
											}
										};

	private static IUpdatable	update	= new IUpdatable() {

											@Override
											public void update( Entity entity, float delta ) {
												CTimer t = CM.Time.get(entity);

												if ( t != null )
													if ( t.time > 0 )
														t.time -= delta;
													else
														grownBerries(entity);
											}
										};
}
