package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CAnimation extends Component implements Poolable {

	public Animation	anim;

	@Override
	public void reset() {
		anim = null;
	}

}
