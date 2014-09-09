package com.nemo9955.starting_fire.game.stage;

import com.badlogic.gdx.scenes.scene2d.Actor;

public interface IActable {

	public void resize( int width, int height );

	public void restart();

	public Actor getActor();

}
