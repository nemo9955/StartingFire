package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CTileField implements Component, Poolable {

	Entity neigbors[] = new Entity[6];

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
