package com.nemo9955.starting_fire;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.nemo9955.starting_fire.states.SplashScreen;
import com.nemo9955.starting_fire.storage.SF;

public class StartingFire extends Game {

	@Override
	public void create() {
		SF.game = this;

		Gdx.app.setLogLevel(Application.LOG_INFO);

		setScreen(new SplashScreen());
	}

	@Override
	public void dispose() {
		SF.spritesBatch.dispose();
		SF.manager.dispose();
		Gdx.app.log("life", "All resources succesfully disposed !");
	}

}
