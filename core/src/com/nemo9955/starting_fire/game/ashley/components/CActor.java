package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CActor extends Component implements Poolable {

	public Actor	actor;

	@Override
	public void reset() {
		actor = null;
	}

}
