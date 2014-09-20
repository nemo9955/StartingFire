package com.nemo9955.starting_fire.game.world;

import com.badlogic.gdx.utils.Array;
import com.nemo9955.starting_fire.game.events.Events;

public enum Resources {

	Meat(Gr.Food),
	Smoked_Meat(Gr.Food),
	Berries(Gr.Food),
	Dried_Berries(Gr.Food),

	Wood(Gr.Gather),
	Clay(Gr.Gather), ;

	private int	amount	= 0;

	Resources(Gr type) {
		type.array.add(this);
	}

	@Override
	public String toString() {
		return this.name().replace('_', ' ');
	}

	public int getAmount() {
		return amount;
	}

	public void addAmount( int difference ) {
		setAmount(difference + amount);
	}

	public void setAmount( int amount ) {
		this.amount = amount;
		// Gdx.app.log("Resources", toString() + " = " + getAmount());
		Events.Berries.call();
	}

	public static void reset() {
		for (Resources res : values())
			res.amount = 0;
	}

	public static enum Gr {
		Food, Armor, Weapon, Gather;

		public Array<Resources>	array	= new Array<Resources>(5);
	}

}
