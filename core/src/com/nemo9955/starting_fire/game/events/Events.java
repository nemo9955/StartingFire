package com.nemo9955.starting_fire.game.events;

import com.badlogic.gdx.utils.Array;

public enum Events {

	Fire_Lit, Berries;

	private Array<IEventListener>	listeners	= new Array<IEventListener>(1);

	public void call() {
		for (IEventListener ev : getListeners())
			ev.called();
	}

	public Array<IEventListener> getListeners() {
		return listeners;
	}

	public void addListener( IEventListener listener ) {
		listeners.add(listener);
	}

	public static void clearAll() {
		for (Events ev : Events.values())
			ev.getListeners().clear();
	}

}
