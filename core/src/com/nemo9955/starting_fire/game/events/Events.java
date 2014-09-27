package com.nemo9955.starting_fire.game.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

public enum Events {

	Fire_Lit,
	Meat(Gr.Food),
	Smoked_Meat(Gr.Food),
	Berries(Gr.Food),
	Dried_Berries(Gr.Food),
	Wood(Gr.Gather),
	Clay(Gr.Gather), ;

	Events(Gr type) {
		type.array.add(this);
	}

	Events() {}

	private Array<IEventListener>	listeners	= new Array<IEventListener>(1);
	private int						quantity	= 0;

	public int getAmount() {
		return quantity;
	}

	public void addOne() {
		setAmount(quantity + 1);
	}

	public void addAmount( int amount ) {
		setAmount(amount + quantity);
	}

	public void setAmount( int amount ) {
		Gdx.app.log("Resources", toString() + " = " + getAmount());
		this.quantity = amount;
		for (IEventListener ev : getListeners())
			ev.called();
	}

	public Array<IEventListener> getListeners() {
		return listeners;
	}

	public void addListener( IEventListener listener ) {
		listeners.add(listener);
	}

	@Override
	public String toString() {
		return this.name().replace('_', ' ');
	}

	public static void reset() {
		for (Events ev : values()) {
			ev.quantity = 0;
			ev.getListeners().clear();
		}
	}

	public static enum Gr {
		Food, Armor, Weapon, Gather;

		public Array<Events>	array	= new Array<Events>(5);
	}

}
