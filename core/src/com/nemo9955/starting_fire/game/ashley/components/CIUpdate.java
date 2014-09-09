package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CIUpdate extends Component implements Poolable {

	public IUpdatable	update;

	@Override
	public void reset() {
		update = null;
	}

	public static interface IUpdatable {

		public void update( float delta );
	}

}
