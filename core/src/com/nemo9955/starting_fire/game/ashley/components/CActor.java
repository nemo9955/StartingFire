package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.nemo9955.starting_fire.game.stage.IActable;

public class CActor extends Component implements Poolable {

	public IActable	actor;

	@Override
	public void reset() {
		actor = null;
	}

}