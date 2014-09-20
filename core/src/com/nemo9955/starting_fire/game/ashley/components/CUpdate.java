package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CUpdate extends Component implements Poolable {

	public IUpdatable	update;

	@Override
	public void reset() {
		update = null;
	}

	public static interface IUpdatable {

		public void update( Entity entity, float delta );
	}

}
