package com.nemo9955.starting_fire.game.events;

import com.badlogic.gdx.utils.Array;

public enum Event {

	Fire_Lit;

	private Array<IEventListener>	listeners	= new Array<IEventListener>(1);

	public Array<IEventListener> getListeners() {
		return listeners;
	}

	public void addListener( IEventListener listener ) {
		listeners.add(listener);
	}

}
