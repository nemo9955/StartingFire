package com.nemo9955.starting_fire.game.ashley;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CAlignment extends Component implements Poolable {

	public int	row, col;

	@Override
	public void reset() {
		row = col = 0;
	}
}
