package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Pool.Poolable;

//TODO implement so it extends Actor
public class CActor /* extends Actor */ implements Poolable, Component {

	public IActable actor;
	private int DEGEABA;

	@Override
	public void reset() {
		actor = null;
	}

	public static interface IActable {

		public void resize(int width, int height);

		public Group getGroup();

	}

}
