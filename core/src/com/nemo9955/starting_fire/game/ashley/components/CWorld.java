package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.nemo9955.starting_fire.game.world.HexWorld;

public class CWorld extends Component implements Poolable {

	public HexWorld	world;

	@Override
	public void reset() {
		world = null;
	}

}
