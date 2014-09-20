package com.nemo9955.starting_fire.game.ashley.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.nemo9955.starting_fire.game.tiles.HexBase;
import com.nemo9955.starting_fire.game.world.World;

public class CInfo extends Component implements Poolable {

	public World	world;
	public Spot		spot	= Spot.empty;
	public HexBase	hex		= null;

	@Override
	public void reset() {
		world = null;
		spot = Spot.empty;
		hex = null;
	}

	public static enum Spot {
		empty, used, coverd;
	}
}
