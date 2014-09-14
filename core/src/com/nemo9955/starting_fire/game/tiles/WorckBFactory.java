package com.nemo9955.starting_fire.game.tiles;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.nemo9955.starting_fire.game.ashley.EntityManager;
import com.nemo9955.starting_fire.game.ashley.components.CIHit.IHitable;
import com.nemo9955.starting_fire.game.stage.DefaultTileActor;

public class WorckBFactory implements IHitable {

	public static WorckBFactory	instance	= new WorckBFactory();

	ChangeListener				listener	= new ChangeListener() {

												@Override
												public void changed( ChangeEvent event, Actor actor ) {

												}
											};

	{

	}

	public void useElement( EntityManager manager ) {

		DefaultTileActor actor = new DefaultTileActor(manager.getEntity());
		actor.getGroup().addListener(listener);

		manager.addActor(actor);
		manager.addHit(instance);
	}

	@Override
	public void hit( Entity ent ) {}

}
