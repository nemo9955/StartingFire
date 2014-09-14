package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CIActor extends Component implements Poolable {

	public IActable	actor;

	@Override
	public void reset() {
		actor = null;
	}

	public static interface IActable {

		public void resize( int width, int height );

		public void restart();

		public Group getGroup();

	}

}
