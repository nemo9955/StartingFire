package com.nemo9955.starting_fire.game.tiles.factories;

import com.badlogic.ashley.core.Entity;
import com.nemo9955.starting_fire.game.ashley.EntityManager;
import com.nemo9955.starting_fire.game.ashley.components.CHit;
import com.nemo9955.starting_fire.game.ashley.components.CHit.IHitable;
import com.nemo9955.starting_fire.game.ashley.components.CInfo.Spot;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.ashley.components.CTimer;
import com.nemo9955.starting_fire.game.ashley.components.CUpdate;
import com.nemo9955.starting_fire.game.ashley.components.CUpdate.IUpdatable;
import com.nemo9955.starting_fire.game.events.Events;
import com.nemo9955.starting_fire.storage.Func;
import com.nemo9955.starting_fire.storage.SF;

public class LumberJack {

	private static float	timer	= 1;
	private static short	amount	= 10;

	public static void useElement( Entity entity ) {
		entity = Func.findSpot(entity, Spot.empty);
		EntityManager manager = CM.Info.get(entity).world.manager;

		manager.addTexture(entity, SF.atlas.findRegion("sand"));
		manager.addHit(entity, hit);
	}

	private static IUpdatable	update	= new IUpdatable() {

											@Override
											public void update( Entity entity, float delta ) {
												CTimer t = CM.Time.get(entity);

												if ( t != null )
													if ( t.time > 0 )
														t.time -= delta;
													else {
														entity.remove(CUpdate.class);
														CM.Info.get(entity).world.manager.addHit(entity, hit);
													}
											}
										};

	private static IHitable		hit		= new IHitable() {

											@Override
											public void hit( Entity entity ) {
												EntityManager manager = CM.Info.get(entity).world.manager;
												manager.addTimer(entity, timer);
												manager.addUpdate(entity, update);

												Events.Wood.addAmount(amount);

												entity.remove(CHit.class);
											}
										};

}
