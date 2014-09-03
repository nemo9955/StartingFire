package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CTexture extends Component implements Poolable {

	public Array<TextureRegion>	tex	= new Array<TextureRegion>(1);

	@Override
	public void reset() {
		tex = null;
	}

}
