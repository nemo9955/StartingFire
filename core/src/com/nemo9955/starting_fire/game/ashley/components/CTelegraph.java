package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CTelegraph extends Component implements Poolable {

	public Telegraph	tel;

	@Override
	public void reset() {
		tel = null;
	}

}
