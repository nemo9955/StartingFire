package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CCollision  implements Poolable,Component {

	public Rectangle	bound	= new Rectangle();
	public Ellipse		colide	= new Ellipse();	// TODO make this more accurate (if needed)

	public void setColide( float x, float y, float width, float height ) {
		bound.set(x, y, width, height);
		colide.set(x + width / 2, y + height / 2, width, height);
	}

	public boolean isInside( float x, float y ) {
		if ( bound.contains(x, y) )
			if ( colide.contains(x, y) )
				return true;
		return false;
	}

	@Override
	public void reset() {
		bound.set(0, 0, 0, 0);
		colide.set(0, 0, 0, 0);
	}

}
