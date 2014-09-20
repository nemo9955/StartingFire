package com.nemo9955.starting_fire.game.tiles;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nemo9955.starting_fire.game.ashley.EntityManager;
import com.nemo9955.starting_fire.game.ashley.components.CInfo.Spot;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.stage.DefaultTileActor;
import com.nemo9955.starting_fire.storage.Func;
import com.nemo9955.starting_fire.storage.SF;

public class WorkBFactory {

	public static void useElement( Entity entity ) {
		entity = Func.findSpot(entity, Spot.empty);
		EntityManager manager = CM.Info.get(entity).world.manager;

		DefaultTileActor actor = new DefaultTileActor(entity);
		actor.getGroup().addListener(listener);

		manager.addActor(entity, actor);
		manager.addTexture(entity, SF.atlas.findRegion("workbench"));

		entity.componentRemoved.add(new Listener<Entity>() {

			@Override
			public void receive( Signal<Entity> signal, Entity object ) {

			}
		});
	}

	private static ChangeListener	listener	= new ChangeListener() {

													@Override
													public void changed( ChangeEvent event, Actor act ) {

													}
												};

}
