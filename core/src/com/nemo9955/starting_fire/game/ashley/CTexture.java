package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CTexture extends Component implements Poolable {

	public TextureRegion	tex;

	@Override
	public void reset() {
		tex = null;
	}

}
