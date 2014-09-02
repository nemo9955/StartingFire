package com.nemo9955.starting_fire.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.nemo9955.starting_fire.storage.SF;

public class MenuController extends ScreenAdapter {

	public StateMachine<MenuController>	mst		= new StackStateMachine<MenuController>(this);

	final Stage							stage	= new Stage(new ScreenViewport());

	@Override
	public void show() {
		// Gdx.app.log("life", "show menuController screen");
		mst.changeState(SF.mainMenu);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render( float delta ) {
		Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mst.update();
		stage.act();
		stage.draw();
	}

	@Override
	public void resize( int width, int height ) {
		stage.getViewport().update(width, height, true);
	}
}
