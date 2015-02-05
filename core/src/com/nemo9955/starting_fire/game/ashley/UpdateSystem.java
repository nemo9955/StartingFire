package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nemo9955.starting_fire.game.ashley.components.CUpdate;
import com.nemo9955.starting_fire.game.ashley.components.CM;

public class UpdateSystem extends IteratingSystem {

	public boolean	update	= true;

	@SuppressWarnings("unchecked")
	public UpdateSystem() {
		super(Family.one(CUpdate.class).get());
	}

	@Override
	public void processEntity( Entity entity, float deltaTime ) {
		CUpdate upd = CM.Upd.get(entity);

		upd.update.update(entity, deltaTime);
	}

	@Override
	public boolean checkProcessing() {
		return update;
	}
}
