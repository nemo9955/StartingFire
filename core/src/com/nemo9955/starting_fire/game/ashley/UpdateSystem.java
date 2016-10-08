package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.nemo9955.starting_fire.game.ashley.components.CMap;
import com.nemo9955.starting_fire.game.ashley.components.CTimer;

public class UpdateSystem extends IteratingSystem {

	public boolean update = true;

	@SuppressWarnings("unchecked")
	public UpdateSystem() {
		super(Family.one(CTimer.class).get());
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		// CUpdate upd = CMap.Upd.get(entity);
		// if (upd != null)
		// upd.update.update(entity, deltaTime);
		CTimer tim = CMap.timer.get(entity);
		if (tim != null) {
			tim.time -= deltaTime;
			if (tim.time <= 0) {
				MessageManager.getInstance().dispatchMessage(tim.msgType);
				entity.remove(CTimer.class);
			}
		}

	}

	@Override
	public boolean checkProcessing() {
		return update;
	}
}
