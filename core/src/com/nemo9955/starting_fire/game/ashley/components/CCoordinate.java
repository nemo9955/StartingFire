package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * uses axial coordinates system
 * 
 * @author nemo9955
 *
 */
public class CCoordinate extends Component implements Poolable {

	/**
	 * goes from right-up to left-down
	 * 
	 * the z axis (in cube coordinates system)
	 */
	public int	r;

	/**
	 * 
	 * goes from top to bottom
	 * 
	 * the x axis (in cube coordinates system)
	 */
	public int	q;

	@Override
	public void reset() {
		r = q = 0;
	}
}
