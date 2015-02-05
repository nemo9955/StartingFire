package com.nemo9955.starting_fire.game.events;

public interface IEventListener {

	public void register();

	public void unregister();

	public void called(int quantity);
}
