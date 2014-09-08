package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.nemo9955.starting_fire.game.world.World;

public class CWorld extends Component implements Poolable {

	public World	world;

	@Override
	public void reset() {
		world = null;
	}

}
