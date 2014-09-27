package com.nemo9955.starting_fire.game.tiles.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nemo9955.starting_fire.game.ashley.EntityManager;
import com.nemo9955.starting_fire.game.ashley.components.CInfo.Spot;
import com.nemo9955.starting_fire.game.ashley.components.CM;
import com.nemo9955.starting_fire.game.stage.DefaultTileActor;
import com.nemo9955.starting_fire.storage.Func;
import com.nemo9955.starting_fire.storage.SF;

public class WorkBench {

	public static void useElement( Entity entity ) {
		entity = Func.findSpot(entity, Spot.empty);
		EntityManager manager = CM.Info.get(entity).world.manager;

		DefaultTileActor actor = new DefaultTileActor(entity);
		actor.getGroup().addListener(listener);

		manager.addActor(entity, actor);
		manager.addTexture(entity, SF.atlas.findRegion("workbench"));

	}

	private static ChangeListener	listener	= new ChangeListener() {

													@Override
													public void changed( ChangeEvent event, Actor act ) {

													}
												};

}
