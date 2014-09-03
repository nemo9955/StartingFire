package com.nemo9955.starting_fire.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.nemo9955.starting_fire.game.world.HexWorld;
import com.nemo9955.starting_fire.storage.SF;
import com.nemo9955.starting_fire.utils.OrthoCamController;

public class Gameplay extends ScreenAdapter {

	private HexWorld			world;

	private OrthographicCamera	camera;
	private OrthoCamController	cameraController;

	{
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		camera.update();

		cameraController = new OrthoCamController(camera);

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(cameraController);

		world = new HexWorld(4, 4, 127, 82);
	}

	@Override
	public void render( float delta ) {
		Gdx.gl.glClearColor(0.25f, 0.25f, 0.25f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		SF.spritesBatch.setProjectionMatrix(camera.combined);
		SF.spritesBatch.begin();
		world.update(delta);
		// System.out.println("game render");
		SF.spritesBatch.end();
	}

	@Override
	public void resize( int width, int height ) {
		camera.setToOrtho(false, width, height);
		camera.update();
	}

	@Override
	public void hide() {
		world.dispose();
	}

	@Override
	public void dispose() {}

}
