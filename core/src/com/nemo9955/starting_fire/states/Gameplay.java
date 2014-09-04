package com.nemo9955.starting_fire.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.nemo9955.starting_fire.game.GamePlayStage;
import com.nemo9955.starting_fire.game.world.HexWorld;
import com.nemo9955.starting_fire.storage.SF;
import com.nemo9955.starting_fire.utils.OrthoCamController;

public class Gameplay extends ScreenAdapter {

	private HexWorld			world;

	private OrthographicCamera	camera;
	private OrthoCamController	cameraController;

	private GamePlayStage		stage;

	{
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		camera.update();

		stage = new GamePlayStage();

		cameraController = new OrthoCamController(camera);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(new InputMultiplexer(stage, cameraController));
		stage.restart();
		world = new HexWorld(4, 4, 127, 82);
	}

	@Override
	public void render( float delta ) {
		Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		SF.spritesBatch.setProjectionMatrix(camera.combined);
		SF.spritesBatch.begin();
		world.manage(delta);
		SF.spritesBatch.end();
		stage.manage(delta);
	}

	@Override
	public void resize( int width, int height ) {
		stage.resize(width, height);
		camera.setToOrtho(false, width, height);
		camera.update();
	}

	@Override
	public void hide() {
		world.dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
