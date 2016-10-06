package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CAnimation /*extends Animation*/ implements Poolable,Component {

//	public CAnimation(float frameDuration, Array<? extends TextureRegion> keyFrames, PlayMode playMode) {
//		super(frameDuration, keyFrames, playMode);
//		// TODO Auto-generated constructor stub
//	}
	private int DEGEABA;

	public Animation	anim;

	@Override
	public void reset() {
		anim = null;
	}

}
