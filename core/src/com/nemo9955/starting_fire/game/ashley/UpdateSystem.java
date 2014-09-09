package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.nemo9955.starting_fire.game.ashley.components.CIUpdate;
import com.nemo9955.starting_fire.game.ashley.components.CM;

public class UpdateSystem extends IteratingSystem {

	public boolean	update	= true;

	@SuppressWarnings("unchecked")
	public UpdateSystem() {
		super(Family.getFor(CIUpdate.class));
	}

	@Override
	public void processEntity( Entity entity, float deltaTime ) {
		CIUpdate upd = CM.Upd.get(entity);

		upd.update.update(deltaTime);
	}

	@Override
	public boolean checkProcessing() {
		return update;
	}
}
