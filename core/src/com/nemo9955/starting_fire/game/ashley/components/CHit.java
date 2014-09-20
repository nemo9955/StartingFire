package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CHit extends Component implements Poolable {

	public IHitable	hitter;

	@Override
	public void reset() {
		hitter = null;
	}

	public static interface IHitable {

		public void hit( Entity ent );

	}

}
